package com.example.memoflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title:String,
    val originalText:String,
    val imagePath: String?=null,
    val summary:String?=null,
    val keywords:String?=null,
    val actionItems:String?=null,
    val status: DocumentStatus = DocumentStatus.PROCESSING,
    val errorMessages: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
