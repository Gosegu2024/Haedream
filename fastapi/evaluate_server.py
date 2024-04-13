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

from konlpy.tag import Okt
from collections import Counter
import re
import tiktoken


## [setting] ################################################################

# 환경변수 설정
os.environ["LANGCHAIN_TRACING_V2"] = "true"
os.environ['LANGCHAIN_API_KEY'] = ''
os.environ['OPENAI_API_KEY'] = ''

llm = ChatOpenAI(model="gpt-4-turbo-preview")
# llm = ChatOpenAI(model="gpt-3.5-turbo-0125")
okt = Okt()

# 불용어 리스트 불러오기
f = open("stop_words.txt", "r", encoding="utf-8")
stop_words = f.read().splitlines()

## [함수] ####################################################################


# 평가(+멀티쓰레딩) 함수
def multithreading_eval(outputData, question_list):
    result_dict = {}
    lock = threading.Lock()

    def query_to_gpt(question):
        output_parser = StrOutputParser()
        prompt = ChatPromptTemplate.from_messages(
            [("system", question), ("user", "{input}")]
        )

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


# 딕셔너리 변환 함수
def set_key_value(result_dict, question_list, key_list):
    final_dict = {}
    for question, key, value in zip(question_list, key_list, result_dict.values()):
        if question in result_dict.keys():
            final_dict[key] = result_dict[question]
    return final_dict


# 토큰수 계산 함수
def count_tokens(sentence, encoding_name):
    encoding = tiktoken.get_encoding(encoding_name)
    num_tokens = len(encoding.encode(sentence))
    return num_tokens


# byte 계산 함수
def byte_count(sentence):
    total_bytes = 0
    for char in sentence:
        # 한글 or 한자
        if "가" <= char <= "힣" or "一" <= char <= "龥":
            total_bytes += 2
        # 영어 or 숫자
        elif char.encode().isalnum():
            total_bytes += 1
        # 공백
        elif char.isspace():
            total_bytes += 1
        # 특수문자 등
        else:
            total_bytes += len(char.encode("utf-8"))
    return total_bytes


# 불용어 제거 함수
def delete_stop_words(nouns, stop_words):
    result = []
    for noun in nouns:
        if noun not in stop_words:
            result.append(noun)
    return result


# 빈도수 카운트 함수
def frequency(sentence):
    normal = okt.normalize(sentence)
    normal = re.sub("[^ㄱ-ㅎㅏ-ㅣ가-힣0-9a-zA-Z ]", "", normal)
    nouns = okt.nouns(normal)
    del_stw = delete_stop_words(nouns, stop_words)
    cnt_nouns = Counter(del_stw)
    return cnt_nouns.most_common(10)


# 영어, 한자 찾는 함수
def find_eng_and_chi(sentence):
    eng_list = re.findall(r"[a-zA-Z]+", sentence)
    chi_list = re.findall(r"[\u4e00-\u9fff]+", sentence)

    if not eng_list and not chi_list:
        return []
    else:
        if not eng_list:
            eng_list = []
        if not chi_list:
            chi_list = []
        return eng_list, chi_list


# DB 저장 함수
class ModelRunner:
    server_url = "http://localhost:8088/save_eval"

    def run_model(
        self,
        inputData,
        outputData,
        username,
        projectName,
        logId,
        checkSummary,
        checkTerminology,
        checkHallucination,
        checkReadability,
        checkReadabilityScore,
        checkPurpose,
        checkPurposeScore,
        checkProblem,
        checkProblemScore,
        checkCreative,
        checkCreativeScore,
        checkContradiction,
        checkContradictionScore,
        HighLightContradiction,
        checkStandard,
        checkPrivacy,
        HighLightPrivacy,
        feedback,
        freqCnt,
        tokenCnt,
        letterCnt,
        byteCnt,
        eng_list,
        chi_list,
    ):

        # 결과를 JSON 형식으로 변환
        json_evaluation = self._format_evaluation(
            inputData,
            outputData,
            username,
            projectName,
            logId,
            checkSummary,
            checkTerminology,
            checkHallucination,
            checkReadability,
            checkReadabilityScore,
            checkPurpose,
            checkPurposeScore,
            checkProblem,
            checkProblemScore,
            checkCreative,
            checkCreativeScore,
            checkContradiction,
            checkContradictionScore,
            HighLightContradiction,
            checkStandard,
            checkPrivacy,
            HighLightPrivacy,
            feedback,
            freqCnt,
            tokenCnt,
            letterCnt,
            byteCnt,
            eng_list,
            chi_list,
        )

        # 결과를 서버로 전송하여 저장
        self._save_to_server(json_evaluation)

        # 결과 반환
        return json_evaluation

    def _format_evaluation(
        self,
        inputData,
        outputData,
        username,
        projectName,
        logId,
        checkSummary,
        checkTerminology,
        checkHallucination,
        checkReadability,
        checkReadabilityScore,
        checkPurpose,
        checkPurposeScore,
        checkProblem,
        checkProblemScore,
        checkCreative,
        checkCreativeScore,
        checkContradiction,
        checkContradictionScore,
        HighLightContradiction,
        checkStandard,
        checkPrivacy,
        HighLightPrivacy,
        feedback,
        freqCnt,
        tokenCnt,
        letterCnt,
        byteCnt,
        eng_list,
        chi_list,
    ):
        # 결과를 JSON 형식으로 포맷팅합니다.
        result = {
            "inputData": inputData,
            "outputData": outputData,
            "username": username,
            "projectName": projectName,
            "logId": logId,
            "evalSummary": checkSummary,
            "evalTerminology": checkTerminology,
            "evalHallucination": checkHallucination,
            "evalReadability": checkReadability,
            "evalReadabilityScore": checkReadabilityScore,
            "evalPurpose": checkPurpose,
            "evalPurposeScore": checkPurposeScore,
            "evalProblem": checkProblem,
            "evalProblemScore": checkProblemScore,
            "evalCreative": checkCreative,
            "evalCreativeScore": checkCreativeScore,
            "evalContradiction": checkContradiction,
            "evalContradictionScore": checkContradictionScore,
            "HighLightContradiction": HighLightContradiction,
            "evalStandard": checkStandard,
            "evalPrivacy": checkPrivacy,
            "HighLightPrivacy": HighLightPrivacy,
            "evalFeedback": feedback,
            "freqCnt": f"{freqCnt}",
            "tokenCnt": tokenCnt,
            "letterCnt": letterCnt,
            "byteCnt": byteCnt,
            "eng_list": f"{eng_list}",
            "chi_list": f"{chi_list}",
        }
        return json.dumps(result)

    def _save_to_server(self, data):
        # 서버로 데이터를 전송하여 저장합니다.

        data2 = {"evalresult": data}
        with requests.post(self.server_url, data=data2, stream=True) as response:
            if response.ok:
                print("Data successfully saved to server.")
            else:
                print("Failed to save data to server.")
                print(response.status_code)


## [FastAPI 서버] ####################################################################

app = FastAPI()


class ProcessData(BaseModel):
    outputData: str
    standard: str
    inputData: str
    username: str
    projectName: str
    logId: str


# 평가
@app.post("/evaluate")
async def receive_data(data: ProcessData):
    outputData = data.outputData
    standard = data.standard
    inputData = data.inputData
    username = data.username
    projectName = data.projectName
    logId = data.logId

    # 질의 설정
    checkSummary = """{input}에서 전체 요약(summary)을 구해줘 
    개행표시는 쓰지말고 <br>태그를 넣어줘. 
    제목과 소제목을 강조해줘. 특수문자 *를 절대로 쓰지 말고 대신 <b> 태그를 넣어서 강조를 해줘. 강조된 text의 뒤쪽에는 <br>태그를 써줘. output을 쓰기 전에 다시 한번 * 특수문자를 썼는지 다시 한번 검토해. 
    강조할때의 예시를 알려줄게
    <br><b>1.강조하고싶은 text</b><br>내용적는곳
    <br><b>2.강조하고싶은 text</b><br>내용적는곳
    <br><b>3.강조하고싶은 text</b><br>내용적는곳
    <br><b>4.강조하고싶은 text</b><br>내용적는곳
    """
    checkTerminology = (
        "{input}에서 "
        + f"""{inputData}와 관련된 전문 용어 5가지만 출력해줘. 출력할 때는 '전문용어: 내용' 형식으로 전문 용어의 자세한 설명과 함께 써줘. 전문용어 앞에는 <br>을 써서 구분을 해줘
        예시를 알려줄게 
        <br><b>전문용어1</b>:내용<br><b>전문용어2</b>:내용..."""
    )
    checkHallucination = "{input}에서 불확실하거나 틀린 정보, 출처가 명확하지 않은 정보(거짓말, hallucination)가 있는지 알려주고 없다면 '불확실한 정보 없음'으로 출력해줘."
    checkReadability = (
        """{input}을 보고 가독성을 향상시킬 수 있는 방법을 최대 네 문장으로 알려줘.
        예시를 알려줄게 
        <br><b>1.가독성을 향상시킬 수 있는 방법 제목1</b><br>내용적는곳
        <br><b>2.가독성을 향상시킬 수 있는 방법 제목2</b><br>내용적는곳
        <br><b>3.가독성을 향상시킬 수 있는 방법 제목3</b><br>내용적는곳
        <br><b>4.가독성을 향상시킬 수 있는 방법 제목4</b><br>내용적는곳
        """
    )
    checkReadabilityScore = "{input}을 보고 가독성 점수를 나타내줘. 점수는 0점부터 10점까지 0.1 단위로 아무말도 붙이지 말고 '1','1.5' 처럼 숫자만 보여줘"
    checkPurpose = "{input}을 보고 목적과 목표가 분명한지 최대 네 문장으로 평가해줘."
    checkPurposeScore = "{input}을 보고 목적과 목표가 분명한지 점수를 나타내줘. 점수는 0점부터 10점까지 0.1 단위로 아무말도 붙이지 말고 '1','1.5' 처럼 숫자만 보여줘"
    checkProblem = (
        "{input}을 보고 문제상황과 해결 방향이 분명한지 최대 네 문장으로 평가해줘."
    )
    checkProblemScore = "{input}을 보고 문제상황과 해결 방향이 분명한지 점수를 나타내줘. 점수는 0점부터 10점까지 0.1 단위로 아무말도 붙이지 말고 '1','1.5' 처럼 숫자만 보여줘"
    checkCreative = (
        "{input}을 보고 "
        + f"{inputData}에 대한 아이디어가 창의적인지, 진부한지 최대 네 문장으로 평가해줘."
    )
    # 여기까지 10개
    checkCreativeScore = (
        "{input}을 보고 "
        + f"{inputData}에 대한 아이디어가 창의적인지, 진부한지 점수를 나타내줘. 점수는 0점부터 10점까지 0.1 단위로 아무말도 붙이지 말고 '1','1.5' 처럼 숫자만 보여줘"
    )
    checkContradiction = (
        "{input}을 보고 전체 글에 모순된 부분은 없는지 최대 네 문장으로 평가해줘."
    )
    checkContradictionScore = "{input}을 보고 전체 글에 모순된 부분은 없는지 점수를 나타내줘. 점수는 0점부터 10점까지 0.1 단위로 아무말도 붙이지 말고 '1','1.5' 처럼 숫자만 보여줘"
    # HighLightContradiction = "{input}을 보고 'A는 사과이다','A는 바나나이다' 처럼 모순 되는 부분이 있다면 아무말도 붙이지 말고 두 문장 모두 span태그로 감싸서 '<span>A는 사과이다</span>','<span>A는 바나나이다</span>' 이런식으로만 출력해줘. 모순되는 부분이 없다면 아무것도 출력하지 말아줘."
    HighLightContradiction = (
        "{input}을 보고 아우말도 붙이지 말고 첫번째 문장 원본만 출력해줘."
    )
    checkStandard = (
        "{input}를 보고 "
        + f"""평가기준 {standard}에 부합하는지 각각 최대 네 문장으로 평가해주고 평가기준도 앞에 같이 나타내줘.
        개행표시는 쓰지말고 <br>태그를 넣어줘. 
    숫자 뒤의 소제목에는 **를 써서 강조하는 대신 <b> 태그를 넣어서 강조를 해주고 강조된 text의 뒤쪽에는 <br>태그를 써줘. 특수문자도 쓰지 마.
    강조할때의 예시를 알려줄게
    <br><b>1.강조하고싶은 text</b><br>피드백내용적는곳
    <br><b>2.강조하고싶은 text</b><br>피드백내용적는곳
    <br><b>3.강조하고싶은 text</b><br>피드백내용적는곳
    <br><b>4.강조하고싶은 text</b><br>피드백내용적는곳
    <br><b>5.강조하고싶은 text</b><br>피드백내용적는곳
    <br><b>6.강조하고싶은 text</b><br>피드백내용적는곳
    <br><b>7.강조하고싶은 text</b><br>피드백내용적는곳"""
    )
    checkPrivacy = "{input}을 보고 전체 글에 '이름 : 홍길동' 이처럼 개인정보가 담겨있는 부분이 있다면 출력해주고 없다면 '개인정보가 유출된 부분이 없습니다'라고 출력해줘."
    # HighLightPrivacy = "{input}을 보고 전체 글에 '이름 : 홍길동' 이처럼 개인정보가 담겨있는 부분이 있다면 아무말도 붙이지 말고 '<span>이름 : 홍길동</span>' 이 예시처럼 span태그로만 감싸서 출력해줘 개인정보가 담겨있는 부분이 없다면 아무것도 출력하지 말아줘."
    HighLightPrivacy = (
        "{input}을 보고 아무말도 붙이지 말고 두번째 문장 원본만 출력해줘."
    )
    feedback = """{input}를 보고 글에 대한 전반적인 피드백을 자세하고 명확하게 해줘. 피드백을 할 때 개행표시는 쓰지말고 <br>태그를 넣어줘. 
    숫자 뒤의 소제목에는 **를 써서 강조하는 대신 <b> 태그를 넣어서 강조를 해주고 강조된 text의 뒤쪽에는 <br>태그를 써줘. 특수문자도 쓰지 마.
    강조할때의 예시를 알려줄게
    <br><b>1.강조하고싶은 text</b><br>피드백내용적는곳
    <br><b>2.강조하고싶은 text</b><br>피드백내용적는곳
    <br><b>3.강조하고싶은 text</b><br>피드백내용적는곳
    <br><b>4.강조하고싶은 text</b><br>피드백내용적는곳
    <br><b>5.강조하고싶은 text</b><br>피드백내용적는곳
    <br><b>6.강조하고싶은 text</b><br>피드백내용적는곳
    <br><b>7.강조하고싶은 text</b><br>피드백내용적는곳
    """

    question_list = [
        checkSummary,
        checkTerminology,
        checkHallucination,
        checkReadability,
        checkReadabilityScore,
        checkPurpose,
        checkPurposeScore,
        checkProblem,
        checkProblemScore,
        checkCreative,
        checkCreativeScore,
        checkContradiction,
        checkContradictionScore,
        HighLightContradiction,
        checkStandard,
        checkPrivacy,
        HighLightPrivacy,
        feedback,
    ]

    result_dict = multithreading_eval(outputData, question_list)

    for key, value in result_dict.items():
        if value is None:
            result_dict[value] = "None"

    key_list = [
        "checkSummary",
        "checkTerminology",
        "checkHallucination",
        "checkReadability",
        "checkReadabilityScore",
        "checkPurpose",
        "checkPurposeScore",
        "checkProblem",
        "checkProblemScore",
        "checkCreative",
        "checkCreativeScore",
        "checkContradiction",
        "checkContradictionScore",
        "HighLightContradiction",
        "checkStandard",
        "checkPrivacy",
        "HighLightPrivacy",
        "feedback",
    ]
    final_dict = set_key_value(result_dict, question_list, key_list)

    freqCnt = frequency(outputData)
    tokenCnt = count_tokens(outputData, "cl100k_base")
    letterCnt = len(outputData)
    byteCnt = byte_count(outputData)
    eng_list, chi_list = find_eng_and_chi(outputData)

    result_list = [
        final_dict,
        freqCnt,
        tokenCnt,
        letterCnt,
        byteCnt,
        eng_list,
        chi_list,
    ]

    # 평가 저장
    runner = ModelRunner()
    runner.run_model(
        inputData,
        outputData,
        username,
        projectName,
        logId,
        final_dict["checkSummary"],
        final_dict["checkTerminology"],
        final_dict["checkHallucination"],
        final_dict["checkReadability"],
        final_dict["checkReadabilityScore"],
        final_dict["checkPurpose"],
        final_dict["checkPurposeScore"],
        final_dict["checkProblem"],
        final_dict["checkProblemScore"],
        final_dict["checkCreative"],
        final_dict["checkCreativeScore"],
        final_dict["checkContradiction"],
        final_dict["checkContradictionScore"],
        final_dict["HighLightContradiction"],
        final_dict["checkStandard"],
        final_dict["checkPrivacy"],
        final_dict["HighLightPrivacy"],
        final_dict["feedback"],
        freqCnt,
        tokenCnt,
        letterCnt,
        byteCnt,
        eng_list,
        chi_list,
    )

    return result_list
