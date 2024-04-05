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

llm = ChatOpenAI(model = "gpt-4-turbo-preview")

## [함수] ####################################################################

# 평가 멀티쓰레딩 함수
def multithreading_eval(outputData, question_list):
    result_dict = {}
    lock = threading.Lock()

    def query_to_gpt(question):
        output_parser = StrOutputParser() 
        prompt = ChatPromptTemplate.from_messages([
            ("system", question),
            ("user", "{input}")
        ])

        chain = prompt | llm | output_parser
        result = chain.invoke({"input": outputData})
        
        with lock:
            result_dict[question] = result

    threads = []
    for question in question_list:
        thread = threading.Thread(target=query_to_gpt, args=(question,))
        thread.start()
        threads.append(thread)

    for thread in threads:
        thread.join()

    return result_dict

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
    
    # 질의 문구
    checkSummary = "{input}에서 전체 요약(summary)을 구해줘"
    checkTerminology = "{input}에서 "+f"{inputData}와 관련된 전문 용어 5가지만 알려줘."
    checkHallucination = "{input}에서 불확실하거나 틀린 정보, 출처가 명확하지 않은 정보(거짓말, hallucination)가 있는지 알려줘"
    checkReadability = "{input}을 보고 가독성을 향상시킬 수 있는 방법을 최대 네 문장으로 알려줘. 그리고 '/' 기호를 넣고 그 뒤에 점수를 나타내줘. 점수는 0점부터 10점까지 0.1 단위로 숫자만 보여줘"
    checkPurpose = "{input}을 보고 목적과 목표가 분명한지 최대 네 문장으로 평가해줘. 그리고 '/' 기호를 넣고 그 뒤에 점수를 나타내줘. 점수는 0점부터 10점까지 0.1 단위로 숫자만 보여줘"
    checkProblem = "{input}을 보고 문제상황과 해결 방향이 분명한지 최대 네 문장으로 평가해줘. 그리고 '/' 기호를 넣고 그 뒤에 점수를 나타내줘. 점수는 0점부터 10점까지 0.1 단위로 숫자만 보여줘"
    checkCreative = "{input}을 보고 "+f"{topic}에 대한 아이디어가 창의적인지, 진부한지 최대 네 문장으로 평가해줘. 그리고 '/' 기호를 넣고 그 뒤에 점수를 나타내줘. 점수는 0점부터 10점까지 0.1 단위로 숫자만 보여줘"
    checkContradiction = "{input}을 보고 전체 글에 모순된 부분은 없는지 최대 네 문장으로 평가해줘. 그리고 '/' 기호를 넣고 그 뒤에 점수를 나타내줘. 점수는 0점부터 10점까지 0.1 단위로 숫자만 보여줘. 그리고 모순된 부분이 있다면 앞에 '/' 기호를 붙이고 해당하는 모순된 부분을 사족 없이 출력해줘."
    checkStandard = "{input}를 보고 " + f"평가기준 {standard}에 부합하는지 각각 최대 네 문장으로 평가해주고 평가기준도 앞에 같이 나타내줘."
    checkprivacy = "{input}을 보고 개인 정보 유출이 있는지 알려줘."
    feedback = "{input}를 보고 글에 대한 전반적인 피드백을 해줘"
    question_list = [checkSummary, checkTerminology, checkHallucination, checkReadability, checkPurpose, checkProblem, checkCreative, checkContradiction, checkStandard, checkprivacy, feedback]
    
    result_dict = multithreading_eval(outputData, question_list)

    result_checkSummary = result_dict[checkSummary]
    result_checkTerminology = result_dict[checkTerminology]
    result_checkHallucination = result_dict[checkHallucination]
    result_checkReadability = result_dict[checkReadability]
    result_checkPurpose = result_dict[checkPurpose]
    result_checkProblem = result_dict[checkProblem]
    result_checkCreative = result_dict[checkCreative]
    result_checkContradiction = result_dict[checkContradiction]
    result_checkStandard = result_dict[checkStandard]
    result_checkprivacy = result_dict[checkprivacy]
    result_feedback = result_dict[feedback]
    
    # 평가 저장
    runner = ModelRunner()
    runner.run_model(result_list[1], result_list[0])
    
    return result_list


