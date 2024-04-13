import requests
import json

class ModelRunner:

    server_url = "http://localhost:8088/save_data"

    def run_model(self, modelName, projectName, inputData, apiKey, outputData):
        warning_message = "주의 : projectName, apiKey를 잘못 입력하면 저장되지 않습니다."
        print("\033[1m" + warning_message + "\033[0m")  # 강조하여 출력

        json_output = self._format_output(modelName, projectName, inputData, outputData, apiKey)
       
        self._save_to_server(json_output)
        
        return json_output

    def _format_output(self, modelName, projectName, inputData, outputData, apiKey):
        result = {
            "modelName": modelName,
            "projectName": projectName,
            "inputData": inputData,
            "outputData": outputData,
            "apiKey": apiKey
        }
        return json.dumps(result)

    def _save_to_server(self, data):
        
        data2 = {"js" : data}
        with requests.post(self.server_url, data=data2, stream=True) as response:
            if response.ok:
                print("Data successfully saved to server.")
            else:
                print("Failed to save data to server.")
                print(response.status_code)


def help():
    print("이 패키지는 해드림의 LLM output 평가를 위해 해드림 서버로 데이터를 전송할 수 있는 모듈 패키지입니다. \n사용을 위해서는 해드림 회원가입과 프로젝트 생성이 필요합니다.\n\n1. 먼저 평가할 모델의 질의를 inputData로 정의하고 결과를 outputData로 정의합니다.\n2. 회원가입 후 발급받은 apiKey를 apiKey로 정의하고 생성한 프로젝트 이름을 projectName으로 정의합니다.\n3. 모델의 이름은 modelName으로 정의합니다.\n4. runner 객체를 생성하고 run_model 함수에 각 매개변수를 채워넣고 실행합니다.\n\n  ❗ 이때 잘못된 projectName, apiKey를 입력하면 데이터가 저장되지 않으니 주의 바랍니다.")