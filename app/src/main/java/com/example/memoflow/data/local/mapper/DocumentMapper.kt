package com.example.memoflow.data.local.mapper

import com.example.memoflow.data.local.entity.DocumentEntity
import com.example.memoflow.domain.model.Document

fun DocumentEntity.toDomain(): Document {
    return Document(
        id = id,
        title = title,
        originalText = originalText,
        imagePath = imagePath,
        summary = summary,
        keywords = keywords,
        actionItems = actionItems,
        status = status,
        errorMessages = errorMessages,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Document.toEntity(): DocumentEntity {
    return DocumentEntity(
        id = id,
        title = title,
        originalText = originalText,
        imagePath = imagePath,
        summary = summary,
        keywords = keywords,
        actionItems = actionItems,
        status = status,
        errorMessages = errorMessages,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}