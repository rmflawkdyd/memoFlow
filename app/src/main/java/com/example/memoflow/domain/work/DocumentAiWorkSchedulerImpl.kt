package com.example.memoflow.domain.work

import android.content.Context
import androidx.work.ExistingWorkPolicy
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf

class DocumentAiWorkSchedulerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DocumentAiWorkScheduler {
    override fun enqueue(documentId: Long) {
        val request = OneTimeWorkRequestBuilder<DocumentAiWorker>()
            .setInputData(
                workDataOf(DocumentAiWorker.KEY_DOCUMENT_ID to documentId)
            )
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "document-ai-$documentId",
            ExistingWorkPolicy.REPLACE,
            request)
    }
}