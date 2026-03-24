package com.example.memoflow.domain.work

interface DocumentAiWorkScheduler {
    fun enqueue(documentId: Long)
}