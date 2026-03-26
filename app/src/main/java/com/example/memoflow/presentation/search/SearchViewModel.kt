package com.example.memoflow.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoflow.domain.repository.DocumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: DocumentRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job?=null

    fun updateQuery(query:String){
        _uiState.update { it.copy(query = query, isLoading = true) }
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            val source = if (query.isBlank()) {
                repository.getAllDocuments()
            } else {
                repository.searchDocuments(query)
            }

            source.collect { documents ->
                _uiState.update {
                    it.copy(
                        results = documents,
                        isLoading = false
                    )
                }
            }
        }
    }


}