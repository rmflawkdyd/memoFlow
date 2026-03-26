package com.example.memoflow.domain.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.memoflow.domain.ai.DocumentAiProcessor
import com.example.memoflow.domain.repository.DocumentRepository
import com.example.memoflow.domain.model.DocumentStatus
import com.example.memoflow.domain.ocr.OcrTextExtractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class DocumentAiWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: DocumentRepository,
    private val ocrTextExtractor: OcrTextExtractor,
    private val documentAiProcessor: DocumentAiProcessor
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val documentId = inputData.getLong(KEY_DOCUMENT_ID,-1L)
        if(documentId == -1L) return Result.failure()

        return runCatching {
            val document = repository.getDocumentById(documentId).first()
                ?: return Result.failure()

            val textToProcess = when{
                document.originalText.isNotBlank()-> document.originalText
                !document.imagePath.isNullOrBlank()->ocrTextExtractor.extractText(document.imagePath)
                else -> throw IllegalStateException("처리할 텍스트 또는 이미지가 없습니다.")
            }

            val aiResult = documentAiProcessor.process(textToProcess)

            repository.updateAiResult(
                id = documentId,
                summary = aiResult.summary,
                keywords = aiResult.keywords,
                actionItems = aiResult.actionItems,
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

    companion object{
        const val KEY_DOCUMENT_ID ="key_document_id"
    }
}
