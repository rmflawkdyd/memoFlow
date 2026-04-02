# MemoFlow

MemoFlow는 텍스트, 이미지, 오디오로 들어온 메모를 저장한 뒤, 비동기 처리 파이프라인을 통해 OCR/STT/AI 요약 결과를 정리해서 보여주는 Android 앱입니다.
단순 메모 저장에 그치지 않고, 첨부 파일을 기반으로 문서 내용을 구조화하고 나중에 다시 확인할 수 있도록 문서 중심 흐름으로 설계했습니다.

## 프로젝트 개요

회의 중 빠르게 적은 메모, 촬영한 문서 이미지, 녹음 파일처럼 입력 형태가 제각각인 정보를 하나의 문서 단위로 저장하고, 이후 AI가 요약/키워드/해야 할 일 아이템으로 정리해주는 흐름을 목표로 했습니다.

## 왜 만들었는가?

메모 앱은 많지만, 실제 사용 상황에서는 다음과 같은 문제가 자주 생깁니다.

- 메모가 텍스트뿐 아니라 이미지, 녹음 파일 등 다양한 형태로 흩어짐
- 나중에 다시 찾으려면 원문을 하나씩 다시 읽어야 함
- 회의 메모나 촬영 문서를 정리하는 데 시간이 많이 듦

MemoFlow는 이 문제를 해결하기 위해, 입력은 간단하게 받고 정리는 비동기로 수행하는 구조를 선택했습니다.

## 주요 기능

### 1. 문서 생성
- 제목과 텍스트 메모를 입력할 수 있습니다.
- 이미지 파일과 오디오 파일을 첨부할 수 있습니다.
- 입력값은 먼저 로컬 DB에 저장됩니다.

### 2. 문서 후처리
- 이미지 첨부가 있으면 OCR을 통해 텍스트를 추출합니다.
- 오디오 첨부가 있으면 STT를 통해 텍스트를 추출합니다.
- 추출된 텍스트와 원문 텍스트를 병합합니다.
- 병합된 내용을 기반으로 AI 요약을 생성합니다.

### 3. 문서 상세 확인
- 원문 텍스트
- 요약
- 키워드
- 해야 할 일
- AI 요약 처리 상태 및 오류 메시지

### 4. 문서 목록 관리
- 저장된 문서 목록 조회
- 검색
- 상태 기반 필터
- 문서 삭제

### 5. 설정
- Wi-Fi에서만 AI 처리
- OCR 언어 설정
  - 자동(기기 언어)
  - 한국어
  - 일본어
  - 중국어
  - 라틴 문자

## 기술 스택

- Kotlin
- Jetpack Compose
- Navigation Compose
- Hilt
- Room
- DataStore
- WorkManager
- ML Kit OCR
- Groq API
- Retrofit / OkHttp

## 아키텍처

프로젝트는 Clean Architecture 기반 presentation / domain / data 계층으로 분리했습니다.

### Presentation
- ViewModel
- 상태 기반 화면 처리

### Domain
- 모델
- Repository 인터페이스
- OCR/STT/AI 처리 추상화
- Worker 스케줄링 추상화

### Data
- Room Entity / DAO / Mapper
- Repository 구현
- ML Kit OCR 구현
- Groq 기반 STT / AI 처리 구현
- DataStore 기반 설정 저장

## 한계와 개선 포인트

- 실패 케이스 UX가 더 정교해질 필요가 있음
- AI 처리 및 결과 개선 필요
  (ON_DEVICE 기능 추가 및 OCR 결과 개선 필요)
- OCR/STT/AI 실패 원인별 복구 전략이 더 필요함

## 실행 방법

### API Key 발급
- https://console.groq.com/keys/ 이동 
- Create API Key 버튼 클릭 및 Key 생성
- gradle.properties 파일 GROQ_API_KEY에 API Key 복사
  (Groq API Key는 프로젝트 파일이 아닌 로컬 환경에 두는 방식을 권장합니다.)

예시:
`~/.gradle/gradle.properties`

```properties
GROQ_API_KEY=YOUR_API_KEY

