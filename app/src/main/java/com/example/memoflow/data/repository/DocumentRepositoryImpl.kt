package com.example.memoflow.data.repository

import com.example.memoflow.data.local.dao.DocumentDao
import com.example.memoflow.data.local.mapper.toDomain
import com.example.memoflow.data.local.mapper.toEntity
import com.example.memoflow.domain.model.Document
import com.example.memoflow.domain.model.DocumentStatus
import com.example.memoflow.domain.repository.DocumentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DocumentRepositoryImpl @Inject constructor(
    private val documentDao: DocumentDao
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
}
