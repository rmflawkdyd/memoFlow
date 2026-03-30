package com.example.memoflow.data.local.mapper

import com.example.memoflow.data.local.entity.DocumentAttachmentEntity
import com.example.memoflow.domain.model.AttachmentType
import com.example.memoflow.domain.model.DocumentAttachment

fun DocumentAttachmentEntity.toDomain(): DocumentAttachment{
    return DocumentAttachment(
        id = id,
        documentId = documentId,
        type = AttachmentType.valueOf(type),
        path = path,
        createdAt = createdAt
    )
}

fun DocumentAttachment.toEntity(): DocumentAttachmentEntity{
    return DocumentAttachmentEntity(
        id = id,
        documentId = documentId,
        type = type.name,
        path = path,
        createdAt = createdAt
    )
}