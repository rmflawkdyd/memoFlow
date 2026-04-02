package com.example.memoflow.domain.model

enum class DocumentStatus {
    QUEUED,
    WAITING_FOR_WIFI,
    PROCESSING,
    DONE,
    FAILED
}
