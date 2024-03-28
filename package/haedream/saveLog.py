import requests
import json

class ModelRunner:

    server_url = "http://localhost:8088/save_data"

    def run_model(self, modelName, projectName, inputData, apiKey, outputData):
        
        # 결과를 JSON 형식으로 변환
        json_output = self._format_output(modelName, projectName, inputData, outputData, apiKey)
        
        # 결과를 서버로 전송하여 저장
        self._save_to_server(json_output)
        
        # 결과 반환
        return json_output

    def _format_output(self, modelName, projectName, inputData, outputData, apiKey):
        # 결과를 JSON 형식으로 포맷팅합니다.
        result = {
            "modelName": modelName,
            "projectName": projectName,
            "inputData": inputData,
            "outputData": outputData,
            "apiKey": apiKey
        }
        return json.dumps(result)

    def _save_to_server(self, data):
        # 서버로 데이터를 전송하여 저장합니다.
        
        data2 = {"js" : data}
        response = requests.post(self.server_url, params=data2)
        if (response.status_code == 200 or response.status_code == 201):
            print("Data successfully saved to server.")
        else:
            print("Failed to save data to server.")
            print(response.status_code)
