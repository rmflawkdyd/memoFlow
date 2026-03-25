package com.example.memoflow.presentation.home

import com.example.memoflow.domain.model.Document
import com.example.memoflow.domain.model.DocumentStatus

enum class HomeFilter{
    ALL,
    PROCESSING,
    DONE,
    FAILED

}
data class HomeUiState(
    val documents:List<Document> = emptyList(),
    val searchQuery:String ="",
    val selectedFilter: HomeFilter = HomeFilter.ALL,
    val isLoading: Boolean = true
){
    val filteredDocuments:List<Document>
        get() = documents.filter { document->
            when(selectedFilter){
                HomeFilter.ALL -> true
                HomeFilter.PROCESSING -> document.status == DocumentStatus.PROCESSING
                HomeFilter.DONE -> document.status == DocumentStatus.DONE
                HomeFilter.FAILED -> document.status == DocumentStatus.FAILED
            }
        }
}

