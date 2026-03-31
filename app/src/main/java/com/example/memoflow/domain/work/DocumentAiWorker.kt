package com.example.memoflow.domain.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.memoflow.domain.ai.DocumentAiProcessor
import com.example.memoflow.domain.model.AttachmentType
import com.example.memoflow.domain.repository.DocumentRepository
import com.example.memoflow.domain.model.DocumentStatus
import com.example.memoflow.domain.ocr.OcrTextExtractor
import com.example.memoflow.domain.stt.AudioTranscriber
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class DocumentAiWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: DocumentRepository,
    private val ocrTextExtractor: OcrTextExtractor,
    private val audioTranscriber: AudioTranscriber,
    private val documentAiProcessor: DocumentAiProcessor
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val documentId = inputData.getLong(KEY_DOCUMENT_ID,-1L)
        if(documentId == -1L) return Result.failure()

        return runCatching {
            val detail = repository.getDocumentDetailById(documentId).first()
                ?: return Result.failure()

            val collectedTexts = mutableListOf<String>()

            if(detail.document.originalText.isNotBlank()){
                collectedTexts += detail.document.originalText
            }

            detail.attachments.forEach { attachment ->
                when(attachment.type){
                    AttachmentType.IMAGE -> {
                        collectedTexts+=ocrTextExtractor.extractText(attachment.path)
                    }

                    AttachmentType.AUDIO->{
                        collectedTexts+=audioTranscriber.transcribe(attachment.path)

                    }
                }
            }
            val mergedText = collectedTexts.joinToString("\n").trim()
            if (mergedText.isBlank()) {
                error("처리할 텍스트를 추출하지 못했습니다.")
            }
            repository.updateOriginalText(
                id = documentId,
                originalText = mergedText
            )
            val aiResult = documentAiProcessor.process(mergedText)

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
