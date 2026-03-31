package com.example.memoflow.data.stt

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

data class GroqTranscriptionResponse(
    val text: String
)

interface GroqSttApi {
    @Multipart
    @POST("openai/v1/audio/")
    suspend fun transcribe(
        @Part file: MultipartBody.Part,
        @Part("model")model: RequestBody,
        @Part("language") language: RequestBody?,
        @Part("response_format") responseFormat: RequestBody
    ):GroqTranscriptionResponse
}