package com.example.memoflow.data.repository

import androidx.work.multiprocess.RemoteWorkContinuation.combine
import com.example.memoflow.data.local.dao.DocumentAttachmentDao
import com.example.memoflow.data.local.dao.DocumentDao
import com.example.memoflow.data.local.mapper.toDomain
import com.example.memoflow.data.local.mapper.toEntity
import com.example.memoflow.domain.model.Document
import com.example.memoflow.domain.model.DocumentAttachment
import com.example.memoflow.domain.model.DocumentDetail
import com.example.memoflow.domain.model.DocumentStatus
import com.example.memoflow.domain.repository.DocumentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DocumentRepositoryImpl @Inject constructor(
    private val documentDao: DocumentDao,
    private val attachmentDao: DocumentAttachmentDao
) : DocumentRepository{
    override fun getAllDocuments(): Flow<List<Document>> {
        return documentDao.getAllDocuments().map { list->
            list.map { it.toDomain() }
        }
    }

    override fun getDocumentById(id: Long): Flow<Document?> {
        return documentDao.getDocumentById(id).map { entity->
            entity?.toDomain()
        }
    }

    override fun searchDocuments(query: String): Flow<List<Document>> {
        return documentDao.searchDocuments(query).map{ list->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insertDocument(document: Document): Long {
        return documentDao.insertDocument(document.toEntity())
    }

    override suspend fun updateDocument(document: Document) {
        documentDao.updateDocument(document.toEntity())
    }

    override suspend fun deleteById(id: Long) {
        documentDao.deleteById(id)
    }

    override suspend fun updateAiResult(
        id: Long,
        summary: String?,
        keywords: String?,
        actionItems: String?,
        status: DocumentStatus,
        errorMessage: String?,
    ) {
        documentDao.updateAiResult(
            id = id,
            summary = summary,
            keywords = keywords,
            actionItems = actionItems,
            status = status.name,
            errorMessage = errorMessage
        )
    }

    override suspend fun updateOriginalText(id: Long, originalText: String) {
        documentDao.updateOriginalText(
            id=id,
            originalText = originalText)

    }

    override suspend fun updateStatus(
        id: Long,
        status: DocumentStatus,
        errorMessage: String?,
    ) {
        documentDao.updateStatus(
            id = id,
            status = status.name,
            errorMessage = errorMessage

        )
    }

    override fun getDocumentDetailById(id: Long): Flow<DocumentDetail?> {
        return combine(
            documentDao.getDocumentById(id),
            attachmentDao.getAttachmentByDocumentId(id)
        ){ documentEntity, attachmentEntities ->
            documentEntity?.let {
                DocumentDetail(
                    document = it.toDomain(),
                    attachments = attachmentEntities.map { attachment -> attachment.toDomain() }
                )
            }
        }
    }

    override suspend fun addAttachments(
        documentId: Long,
        attachments: List<DocumentAttachment>,
    ) {
        attachmentDao.insertAttachment(
            attachments.map{
                it.copy(documentId = documentId).toEntity()
            }
        )
    }
}
