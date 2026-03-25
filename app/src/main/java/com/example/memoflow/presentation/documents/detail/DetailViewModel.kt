package com.example.memoflow.presentation.documents.detail

import androidx.compose.runtime.MutableState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoflow.domain.repository.DocumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DocumentRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val documentId: Long = checkNotNull(savedStateHandle["documentId"])
    private val _uiState = MutableStateFlow(DetailUiState())

    val uiState : StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        observeDocument()
    }

    private fun observeDocument(){
        viewModelScope.launch {
            repository.getDocumentById(documentId).collect { document ->
                _uiState.value = DetailUiState(
                    document =document,
                    isLoading = false
                )
            }
        }
    }
}