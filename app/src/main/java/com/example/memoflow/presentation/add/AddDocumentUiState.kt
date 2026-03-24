package com.example.memoflow.presentation.add

data class AddDocumentUiState(
    val title:String ="",
    val originalText:String ="",
    val imagePath:String?=null,
    val isSaving: Boolean= false,
    val isSaved: Boolean = false,
    val errorMessage:String?=null
)
