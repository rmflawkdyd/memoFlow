package com.example.memoflow.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "document_attachments",
    foreignKeys = [
        ForeignKey(
            entity = DocumentEntity::class,
            parentColumns = ["id"],
            childColumns = ["documentId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("documentId")])

data class DocumentAttachmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val documentId: Long,
    val type: String,
    val path:String,
    val createdAt: Long = System.currentTimeMillis()
)