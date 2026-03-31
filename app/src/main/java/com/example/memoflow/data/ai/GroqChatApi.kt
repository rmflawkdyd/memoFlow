package com.example.memoflow.data.ai

import retrofit2.http.Body
import retrofit2.http.POST

data class GroqMessage(
    val role: String,
    val content: String
)

data class GroqChatRequest(
    val model: String,
    val messages: List<GroqMessage>,
    val temperature: Double = 0.2
)

data class GroqChoice(
    val message: GroqMessage
)

data class GroqChatResponse(
    val choices: List<GroqChoice>
)


interface GroqChatApi {
    @POST("openai/v1/chat/completions")
    suspend fun complete(
        @Body request: GroqChatRequest
    ): GroqChatResponse
}