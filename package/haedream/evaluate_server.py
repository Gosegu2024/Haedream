from fastapi import FastAPI
from pydantic import BaseModel

import os
from langchain_openai import ChatOpenAI
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.output_parsers import StrOutputParser
import threading

from haedream import ModelRunner
import requests
import json

## [setting] ################################################################

# 환경변수 설정
os.environ['LANGCHAIN_TRACING_V2'] = 'true'

os.environ['LANGCHAIN_API_KEY'] = 'ls__8763f0bab4ed4414b79d5bd51a84a4b8'
os.environ['OPENAI_API_KEY'] = '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'

llm = ChatOpenAI(model = "gpt-4-turbo-preview")

## [함수] ####################################################################

# 평가 gpt 질의 함수
def query_to_gpt(outputData, question, result_list, lock):
    output_parser = StrOutputParser() 
    prompt = ChatPromptTemplate.from_messages([
        ("system", question),
        ("user", "{input}")
    ])

    chain = prompt | llm | output_parser
    result = chain.invoke({"input": outputData})
    
    with lock:
        result_list.append(result)

# 평가 멀티쓰레딩 함수
def multithreading_eval(outputData, question_list):
    result_list = []
    lock = threading.Lock()

    threads = []
    for question in question_list:
        thread = threading.Thread(target=query_to_gpt, args=(outputData, question, result_list, lock))
        thread.start()
        threads.append(thread)

    for thread in threads:
        thread.join()

    return result_list

# 평가 실행 함수
def execute_evaluation(outputData, question_list):
    result_list = multithreading_eval(outputData, question_list)
    return result_list

# DB 저장 함수
class ModelRunner:
    server_url = "http://localhost:8088/save_eval"

    def run_model(self, evaluateStandard, result_feedback):
        
        # 결과를 JSON 형식으로 변환
        json_evaluation = self._format_evaluation(evaluateStandard, result_feedback)
        
        # 결과를 서버로 전송하여 저장
        self._save_to_server(json_evaluation)
        
        # 결과 반환
        return json_evaluation

    def _format_evaluation(self, evaluateStandard, result_feedback):
        # 결과를 JSON 형식으로 포맷팅합니다.
        result = {
            "standard": evaluateStandard,
            "evalFeedback": result_feedback
        }
        return json.dumps(result)

    def _save_to_server(self, data):
        # 서버로 데이터를 전송하여 저장합니다.
        
        data2 = {"evalresult" : data}
        with requests.post(self.server_url, data=data2, stream=True) as response:
            if response.ok:
                print("Data successfully saved to server.")
            else:
                print("Failed to save data to server.")
                print(response.status_code)

## [FastAPI 서버] ####################################################################

app = FastAPI()

class ProcessData(BaseModel):
    outputdata: str
    standard: str

# 평가
@app.post("/evaluate")
async def receive_data(data: ProcessData):
    outputdata = data.outputdata
    standard = data.standard
    
    # 질의 설정
    feedback = "{input}를 보고 글에 대한 전반적인 피드백을 해줘"
    standard_feedback = "{input}를 보고 " + f"평가기준 {standard}에 부합하는지 알려줘"
    question_list = [feedback, standard_feedback]
    
    result_list = execute_evaluation(outputdata, question_list)
    
    # 평가 저장
    runner = ModelRunner()
    runner.run_model(result_list[1], result_list[0])
    
    return result_list


