package com.example.memoflow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memoflow.data.local.entity.DocumentAttachmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentAttachmentDao {
    @Query("SELECT * FROM document_attachments WHERE documentId = :documentId ORDER BY createdAt ASC")
    fun getAttachmentByDocumentId(documentId: Long): Flow<List<DocumentAttachmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttachment(attachment: List<DocumentAttachmentEntity>)
}