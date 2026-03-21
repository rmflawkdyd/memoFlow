package com.example.memoflow.ui.model

val fakeDocuments = listOf(
    DocumentUiModel(
        id = 1L,
        title = "주간 기획 회의",
        type = DocumentType.AUDIO,
        summary = "다음 스프린트 우선순위와 배포 일정을 정리한 회의 내용",
        tags = listOf("회의", "일정", "우선순위"),
        status = ProcessingStatus.DONE,
        createdAt = "10분 전",
    ),
    DocumentUiModel(
        id = 2L,
        title = "영수증 스캔 문서",
        type = DocumentType.IMAGE,
        summary = "OCR 추출 완료, 비용 내역 분류 대기 중",
        tags = listOf("OCR", "비용", "보관"),
        status = ProcessingStatus.PROCESSING,
        createdAt = "1시간 전",
    ),
    DocumentUiModel(
        id = 3L,
        title = "아이디어 메모",
        type = DocumentType.TEXT,
        summary = "오프라인 우선 검색 UX 개선 아이디어 정리",
        tags = listOf("아이디어", "검색", "UX"),
        status = ProcessingStatus.DONE,
        createdAt = "어제",
    ),
)