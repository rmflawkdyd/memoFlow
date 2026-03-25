package com.example.memoflow.presentation.documents.detail

import com.example.memoflow.domain.model.Document

data class DetailUiState(
    val document: Document?=null,
    val isLoading: Boolean = true
)
