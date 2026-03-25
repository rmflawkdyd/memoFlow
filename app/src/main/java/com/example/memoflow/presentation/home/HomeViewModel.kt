package com.example.memoflow.presentation.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoflow.domain.repository.DocumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DocumentRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState : StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var observeJob: Job?=null

    init {
        observeDocuments()
    }
    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        observeDocuments()
    }

    fun updateFilter(filter: HomeFilter){
        _uiState.update { it.copy(selectedFilter = filter) }
    }

    private fun observeDocuments(){
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            val query = _uiState.value.searchQuery.trim()
            val source = if(query.isBlank()){
                repository.getAllDocuments()
            }else{
                repository.searchDocuments(query)
            }

            source.collectLatest { documents->
                _uiState.update {
                    it.copy(
                        documents = documents,
                        isLoading = false
                    )
                }
            }
        }
    }
}