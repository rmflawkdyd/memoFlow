package com.example.memoflow.domain.model

data class DocumentAttachment(
    val id:Long,
    val documentId: Long,
    val type: AttachmentType,
    val path: String,
    val createdAt: Long,
)
