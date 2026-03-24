package com.example.memoflow.presentation.list

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoflow.domain.repository.DocumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DocumentListViewModel @Inject constructor(
    private val repository: DocumentRepository
): ViewModel(){
    val uiState: StateFlow<DocumentListUiState> =
        repository.getAllDocuments()
            .map { documents ->
                DocumentListUiState(
                    documents = documents,
                    isLoading = false
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = DocumentListUiState()
            )
}