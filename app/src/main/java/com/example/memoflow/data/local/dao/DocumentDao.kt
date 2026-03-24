package com.example.memoflow.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.memoflow.data.local.entity.DocumentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentDao {
    @Query("SELECT * FROM documents ORDER BY  createdAt DESC")
    fun getAllDocuments(): Flow<List<DocumentEntity>>

    @Query("SELECT * FROM documents WHERE id = :id")
    fun getDocumentById(id: Long):Flow<DocumentEntity?>

    @Query("""
        SELECT * FROM documents
        WHERE title LIKE '%' || :query || '%'
           OR originalText LIKE '%' || :query || '%'
           OR summary LIKE '%' || :query || '%'
           OR keywords LIKE '%' || :query || '%'
        ORDER BY createdAt DESC
    """)
    fun searchDocuments(query: String): Flow<List<DocumentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: DocumentEntity): Long

    @Update
    suspend fun updateDocument(document: DocumentEntity)

    @Delete
    suspend fun deleteDocument(document: DocumentEntity)

    @Query("DELETE FROM documents WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("""
        UPDATE documents
        SET summary = :summary,
            keywords = :keywords,
            actionItems = :actionItems,
            status = :status,
            errorMessages = :errorMessage,
            updatedAt = :updatedAt
        WHERE id = :id
    """)
    suspend fun updateAiResult(
        id: Long,
        summary: String?,
        keywords: String?,
        actionItems: String?,
        status: String,
        errorMessage: String?,
        updatedAt: Long = System.currentTimeMillis()
    )

    @Query("""
        UPDATE documents
        SET status = :status,
            errorMessages = :errorMessage,
            updatedAt = :updatedAt
        WHERE id = :id
    """)
    suspend fun updateStatus(
        id: Long,
        status: String,
        errorMessage: String?,
        updatedAt: Long = System.currentTimeMillis()
    )

}