package com.example.memoflow.presentation.list

import com.example.memoflow.domain.model.Document

data class DocumentListUiState(
    val documents:List<Document> = emptyList(),
    val isLoading: Boolean = true
)
