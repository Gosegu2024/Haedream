이 패키지는 해드림의 LLM output 평가를 위해 해드림 서버로 데이터를 전송할 수 있는 모듈 패키지입니다. 사용을 위해서는 해드림 회원가입과 프로젝트 생성이 필요합니다.

먼저 평가할 모델의 질의를 inputData로 정의하고 결과를 outputData로 정의합니다.
회원가입 후 발급받은 apiKey를 apiKey로 정의하고 생성한 프로젝트 이름을 projectName으로 정의합니다.
모델의 이름은 modelName으로 정의합니다.
runner 객체를 생성하고 run_model 함수에 각 매개변수를 채워넣고 실행합니다.
이때 잘못된 projectName, apiKey를 입력하면 데이터가 저장되지 않으니 주의 바랍니다.
