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
            if(searchQuery.isBlank()){
                true
            }else{
                val query = searchQuery.trim()
                document.title.contains(query, ignoreCase = true) ||
                        document.originalText.contains(query, ignoreCase = true) ||
                        (document.summary?.contains(query, ignoreCase = true) == true) ||
                        (document.keywords?.contains(query, ignoreCase = true) == true)
            }

        }.filter { document ->
                when(selectedFilter){
                    HomeFilter.ALL -> true
                    HomeFilter.PROCESSING -> document.status == DocumentStatus.PROCESSING
                    HomeFilter.DONE -> document.status == DocumentStatus.DONE
                    HomeFilter.FAILED -> document.status == DocumentStatus.FAILED
                }
            }

    val hasAnyDocuments: Boolean
        get() = documents.isNotEmpty()

    val hasActiveSearchOrFilter: Boolean
        get() = searchQuery.isNotBlank() || selectedFilter != HomeFilter.ALL
}

