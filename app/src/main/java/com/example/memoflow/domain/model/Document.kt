package com.example.memoflow.domain.model

data class Document(
    val id: Long,
    val title:String,
    val originalText:String,
    val imagePath: String?,
    val summary:String?,
    val keywords:String?,
    val actionItems:String?,
    val status: DocumentStatus,
    val errorMessages: String?,
    val createdAt: Long ,
    val updatedAt: Long
)
