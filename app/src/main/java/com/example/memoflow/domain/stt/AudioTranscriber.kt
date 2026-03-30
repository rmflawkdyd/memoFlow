package com.example.memoflow.domain.stt

interface AudioTranscriber {
    suspend fun transcribe(audioPath: String): String
}