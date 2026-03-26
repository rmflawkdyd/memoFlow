package com.example.memoflow.domain.ocr

interface OcrTextExtractor {
    suspend fun extractText(imagePath:String): String
}