package com.example.memoflow.domain.repository

import com.example.memoflow.domain.model.Document
import com.example.memoflow.domain.model.DocumentAttachment
import com.example.memoflow.domain.model.DocumentDetail
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

    suspend fun updateOriginalText(
        id: Long,
        originalText: String
    )

    suspend fun updateStatus(
        id: Long,
        status: DocumentStatus,
        errorMessage: String?
    )

    fun getDocumentDetailById(id: Long): Flow<DocumentDetail?>
    suspend fun addAttachments(documentId: Long, attachments: List<DocumentAttachment>)


}
