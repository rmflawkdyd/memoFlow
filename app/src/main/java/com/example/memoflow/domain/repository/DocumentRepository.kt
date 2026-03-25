package com.example.memoflow.domain.repository

import com.example.memoflow.domain.model.Document
import com.example.memoflow.domain.model.DocumentStatus
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {
    fun getAllDocuments(): Flow<List<Document>>
    fun getDocumentById(id: Long):Flow<Document?>
    fun searchDocuments(query: String): Flow<List<Document>>

    suspend fun insertDocument(document: Document): Long
    suspend fun updateDocument(document: Document)
    suspend fun deleteById(id: Long)

    suspend fun updateAiResult(
        id: Long,
        summary: String?,
        keywords: String?,
        actionItems:String?,
        status: DocumentStatus,
        errorMessage: String?
    )
}
