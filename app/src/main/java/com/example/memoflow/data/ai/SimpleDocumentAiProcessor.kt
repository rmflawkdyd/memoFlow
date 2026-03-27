package com.example.memoflow.data.ai

import com.example.memoflow.domain.ai.DocumentAiProcessor
import com.example.memoflow.domain.ai.DocumentAiResult
import javax.inject.Inject

class SimpleDocumentAiProcessor @Inject constructor() : DocumentAiProcessor {
    override suspend fun process(text: String): DocumentAiResult {
        val normalized = text.trim()
        return DocumentAiResult(
            summary = normalized.take(200).ifBlank { "요약할 내용이 없습니다." },
            keywords = normalized
                .split(" ", "\n", ",")
                .filter { it.isNotBlank() }
                .distinct()
                .take(5)
                .joinToString(","),
            actionItems = if (normalized.contains("해야")) "해야 할 일 확인" else ""
        )
    }
}