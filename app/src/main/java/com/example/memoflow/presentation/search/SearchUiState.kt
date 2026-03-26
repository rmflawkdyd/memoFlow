package com.example.memoflow.presentation.search

import com.example.memoflow.domain.model.Document

data class SearchUiState(
    val query: String ="",
    val results:List<Document> = emptyList(),
    val isLoading: Boolean = false
)
