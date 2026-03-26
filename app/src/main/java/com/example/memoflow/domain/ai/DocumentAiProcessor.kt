package com.example.memoflow.domain.ai

data class DocumentAiResult(
    val summary: String,
    val keywords: String,
    val actionItems: String
)

interface DocumentAiProcessor{
    suspend fun process(text:String):DocumentAiResult
}