package com.example.memoflow.presentation.add

import com.example.memoflow.domain.model.AttachmentType


data class PendingAttachment(
    val type: AttachmentType,
    val path: String
)

data class AddDocumentUiState(
    val title: String = "",
    val originalText: String = "",
    val attachments: List<PendingAttachment> = emptyList(),
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)
