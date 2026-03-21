package com.example.memoflow.ui.model

enum class DocumentType{
    TEXT,IMAGE,AUDIO
}

enum class ProcessingStatus{
    PROCESSING,DONE,FAILED
}

data class DocumentUiModel(
    val id:Long,
    val title: String,
    val type: DocumentType,
    val summary: String,
    val tags: List<String>,
    val status: ProcessingStatus,
    val createdAt: String,
)