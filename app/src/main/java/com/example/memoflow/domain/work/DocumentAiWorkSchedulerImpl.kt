package com.example.memoflow.domain.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf

class DocumentAiWorkSchedulerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DocumentAiWorkScheduler {
    override fun enqueue(documentId: Long,wifiOnly: Boolean) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(
                if (wifiOnly) NetworkType.UNMETERED else NetworkType.CONNECTED
            )
            .build()

        val request = OneTimeWorkRequestBuilder<DocumentAiWorker>()
            .setConstraints(constraints)
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