package com.example.memoflow.ui.screen.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.memoflow.presentation.add.AddDocumentViewModel


@Composable
fun AddDocumentScreen(
    onSaved:()-> Unit,
    viewModel: AddDocumentViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState.isSaved) {
        if(uiState.isSaved){
            viewModel.clearSavedState()
            onSaved()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.title,
            onValueChange = viewModel::updateTitle,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("제목") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.originalText,
            onValueChange = viewModel::updateOriginalText,
            modifier = Modifier
                .fillMaxWidth().weight(1f),
            label = {Text("내용")}
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = viewModel::saveDocument,
            enabled = !uiState.isSaving,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(if(uiState.isSaving)"저자ㅣㅇ 중..." else "저장")
        }

        uiState.errorMessage?.let{
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it)
        }
    }
}