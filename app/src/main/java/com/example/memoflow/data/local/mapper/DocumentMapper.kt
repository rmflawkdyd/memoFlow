package com.example.memoflow.data.local.mapper

import com.example.memoflow.data.local.entity.DocumentEntity
import com.example.memoflow.data.local.entity.DocumentStatus as EntityDocumentStatus
import com.example.memoflow.domain.model.Document
import com.example.memoflow.domain.model.DocumentStatus as DomainDocumentStatus

fun DocumentEntity.toDomain(): Document {
    return Document(
        id = id,
        title = title,
        originalText = originalText,
        summary = summary,
        keywords = keywords,
        actionItems = actionItems,
        status = status.toDomain(),
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
        summary = summary,
        keywords = keywords,
        actionItems = actionItems,
        status = status.toEntity(),
        errorMessages = errorMessages,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

private fun EntityDocumentStatus.toDomain(): DomainDocumentStatus {
    return when (this) {
        EntityDocumentStatus.QUEUED -> DomainDocumentStatus.QUEUED
        EntityDocumentStatus.WAITING_FOR_WIFI -> DomainDocumentStatus.WAITING_FOR_WIFI
        EntityDocumentStatus.PROCESSING -> DomainDocumentStatus.PROCESSING
        EntityDocumentStatus.DONE -> DomainDocumentStatus.DONE
        EntityDocumentStatus.FAILED -> DomainDocumentStatus.FAILED
    }
}

private fun DomainDocumentStatus.toEntity(): EntityDocumentStatus {
    return when (this) {
        DomainDocumentStatus.QUEUED -> EntityDocumentStatus.QUEUED
        DomainDocumentStatus.WAITING_FOR_WIFI -> EntityDocumentStatus.WAITING_FOR_WIFI
        DomainDocumentStatus.PROCESSING -> EntityDocumentStatus.PROCESSING
        DomainDocumentStatus.DONE -> EntityDocumentStatus.DONE
        DomainDocumentStatus.FAILED -> EntityDocumentStatus.FAILED
    }
}
