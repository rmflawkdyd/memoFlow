package com.example.memoflow.data.ai

import com.example.memoflow.domain.ai.DocumentAiProcessor
import com.example.memoflow.domain.ai.DocumentAiResult
import javax.inject.Inject
import org.json.JSONObject

class GroqDocumentAiProcessor @Inject constructor(
    private val groqChatApi: GroqChatApi
): DocumentAiProcessor{
    override suspend fun process(text: String): DocumentAiResult {
        val prompt = """
            다음 문서를 분석해서 JSON으로만 답하세요.
            형식:
            {"summary":"","keywords":"","actionItems":""}

            문서:
            $text
        """.trimIndent()

        val response = groqChatApi.complete(
            GroqChatRequest(
                model = "llama-3.1-8b-instant",
                messages = listOf(
                    GroqMessage("system", "당신은 문서를 구조화해서 요약하는 도우미입니다."),
                    GroqMessage("user", prompt)
                )

            )
        )

        val content = response.choices.firstOrNull()?.message?.content
            ?: error("AI 응답이 비어 있습니다.")

        val normalized = content
            .removePrefix("```json")
            .removePrefix("```")
            .removeSuffix("```")
            .trim()

        val json = JSONObject(normalized)

        return DocumentAiResult(
            summary = json.getString("summary"),
            keywords = json.getString("keywords"),
            actionItems = json.optString("actionItems")
        )
    }
}