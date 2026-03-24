package com.example.memoflow.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoflow.data.local.entity.DocumentStatus
import com.example.memoflow.domain.model.Document
import com.example.memoflow.domain.repository.DocumentRepository
import com.example.memoflow.domain.work.DocumentAiWorkScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDocumentViewModel @Inject constructor(
    private val repository: DocumentRepository,
    private val workScheduler: DocumentAiWorkScheduler
): ViewModel(){
    private val _uiState= MutableStateFlow(AddDocumentUiState())
    val uiState: StateFlow<AddDocumentUiState> = _uiState.asStateFlow()

    fun updateTitle(title:String){
        _uiState.update { it.copy(title = title) }
    }

    fun updateOriginalText(text: String){
        _uiState.update { it.copy(originalText = text) }
    }

    fun saveDocument(){
        val current = _uiState.value

        if(current.title.isBlank()&& current.originalText.isBlank() && current.imagePath.isNullOrBlank()){
            _uiState.update { it.copy(errorMessage = "내용을 입력해주세요.") }
            return
        }

        viewModelScope.launch {
            runCatching {
                _uiState.update { it.copy(isSaving = true, errorMessage = null) }
                val now = System.currentTimeMillis()
                val document= Document(
                    id = 0L,
                    title = current.title.ifBlank { "제목 없음" },
                    originalText = current.originalText,
                    imagePath = current.imagePath,
                    summary = null,
                    keywords = null,
                    actionItems = null,
                    status = DocumentStatus.PROCESSING,
                    errorMessages = null,
                    createdAt = now,
                    updatedAt = now
                )

                val documentId = repository.insertDocument(document)
                workScheduler.enqueue(documentId)

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        isSaved = true
                    )
                }
            }.onFailure { e->
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage = e.message ?: "문서 저장 중 오류가 발생했습니다."
                    )
                }
            }
        }
    }

    fun clearSavedState(){
        _uiState.update { it.copy(isSaved = false) }
    }

    fun clearError(){
        _uiState.update { it.copy(errorMessage = null) }
    }

}