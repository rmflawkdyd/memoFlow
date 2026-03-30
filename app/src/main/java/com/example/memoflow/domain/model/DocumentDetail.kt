package com.example.memoflow.domain.model

data class DocumentDetail(
    val document: Document,
    val attachments:List<DocumentAttachment>
)
