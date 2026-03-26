package com.example.memoflow.data.ai

import com.example.memoflow.domain.ai.DocumentAiProcessor
import com.example.memoflow.domain.ai.DocumentAiResult
import javax.inject.Inject

class FakeDocumentAiProcessor @Inject constructor(): DocumentAiProcessor {
    override suspend fun process(text: String): DocumentAiResult {
        return DocumentAiResult(
            summary = text.take(100).ifBlank { "요약할 내용이 없습니다" },
            keywords = if (text.isBlank()) "" else "메모,요약,문서",
            actionItems = if (text.isBlank()) "" else "검토하기"

        )

    }
}