package com.example.memoflow.domain.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.memoflow.domain.repository.DocumentRepository
import com.example.memoflow.domain.model.DocumentStatus
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class DocumentAiWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: DocumentRepository
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val documentId = inputData.getLong(KEY_DOCUMENT_ID,-1L)
        if(documentId == -1L) return Result.failure()

        return runCatching {
            val document = repository.getDocumentById(documentId).first()
                ?: return Result.failure()

            val summary = makeSummary(document.originalText)
            val keywords = extractKeywords(document.originalText)
            val actionItems = extractActionItems(document.originalText)

            repository.updateAiResult(
                id = documentId,
                summary = summary,
                keywords = keywords,
                actionItems = actionItems,
                status = DocumentStatus.DONE,
                errorMessage = null
            )

            Result.success()
        }.getOrElse { e ->
            repository.updateAiResult(
                id = documentId,
                summary = null,
                keywords = null,
                actionItems = null,
                status = DocumentStatus.FAILED,
                errorMessage = e.message ?: "AI 처리 실패"
            )
            Result.failure()
        }
    }

    private fun makeSummary(text:String): String{
        return if (text.isBlank()) {
            "요약할 내용이 없습니다."
        } else {
            text.take(100)
        }
    }

    private fun extractKeywords(text: String): String{
        return if (text.isBlank()) "" else "메모,요약,문서"
    }

    private fun extractActionItems(text: String): String{
        return if (text.isBlank()) "" else "검토하기"
    }

    companion object{
        const val KEY_DOCUMENT_ID ="key_document_id"
    }
}
