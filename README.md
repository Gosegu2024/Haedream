# 👨‍👨‍👧‍👦 웹사이트 기반 작문 LLM 서비스 평가•개선도구 <해드림> 👨‍👨‍👧‍👦

개발 일정 : **2024.03.15 ~ 2024.04.15**

사용언어 : <img src="https://img.shields.io/badge/java-007396?style=flat-square&logo=java&logoColor=white"/> <img src="https://img.shields.io/badge/Python-3776AB?style=flat-square&logo=Python&logoColor=white"/> <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=javascript&logoColor=black"/> <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white"/> <img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white"/>

사용 프레임워크 & 라이브러리 : <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white"> <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white"> <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat-square&logo=Thymeleaf&logoColor=white"> <img src="https://img.shields.io/badge/jQuery-0769AD?style=flat-square&logo=jQuery&logoColor=white"> 

사용 API : <img src="https://img.shields.io/badge/fastapi-009688?style=flat-square&logo=fastapi&logoColor=white"> <img src="https://img.shields.io/badge/pypi-3775A9?style=flat-square&logo=pypi&logoColor=white">  GPT4, LangChain </br></br>

> 주제만 입력하면 기획서를 생성해주는 써드림! 👉 [해드림 바로가기](https://haedream-pafljma3ia-du.a.run.app) </br></br>

![해드림 로고](./해드림_깃헙/KakaoTalk_20240312_154237983_04.png) </br></br>

## 📃 Description
작문 LLM 서비스의 결과물을 평가,개선 할 수 있는 해드림입니다. </br>
모듈을 사용해 생성형 모델의 결과를 받아와 사용자가 설정한 평가기준과 해드림의 평가지표를 기준으로 평가 한 뒤,점수와 피드백을 제공합니다. </br> 최근 5건의 평가결과를 모아 그래프로 시각화까지 해줍니다!

## Demo
![해드림 예시1](./해드림_깃헙/demo1.png) </br>
![해드림 예시2](./해드림_깃헙/demo2.png) </br>
![해드림 예시3](./해드림_깃헙/demo3.png) </br>
![프로세스코드1](./해드림_깃헙/cap2.png) </br>
![프로세스코드2](./해드림_깃헙/cap3.png) </br>

## ⭐ Main Feature
### 모듈로 로그 전송
  - PyPI로 제작한 모듈을 통해 생성형 모델의 인풋데이터, 아웃풋데이터, 모델이름, 프로젝트 이름, api-key 정보 해드림으로 전송
### 평가 및 피드백
  - 사용자의 평가기준과 해드림의 평가지표를 기준으로 평가해 점수화와 피드백 제공 (히스토리 기능 제공)
### 분석 시각화
  - 최근 결과 5건을 모아 Chart.js로 시각화하여 제공
### 챗봇
  - 챗봇을 이용해 사용 가이드 제공

## 🔨 Server Architecture
![아키텍쳐 사진](./해드림_깃헙/아키텍처.png)
