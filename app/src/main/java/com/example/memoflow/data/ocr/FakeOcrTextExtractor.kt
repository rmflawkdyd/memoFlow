package com.example.memoflow.data.ocr

import com.example.memoflow.domain.ocr.OcrTextExtractor
import javax.inject.Inject

class FakeOcrTextExtractor @Inject constructor(): OcrTextExtractor {
    override suspend fun extractText(imagePath: String): String {
        return "OCR 추출 텍스트 예시"
    }
}