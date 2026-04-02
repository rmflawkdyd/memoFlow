package com.example.memoflow.ui.screen.add

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.memoflow.domain.model.AttachmentType
import com.example.memoflow.presentation.add.AddDocumentViewModel


@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDocumentScreen(
    onBack:()-> Unit,
    onSaved:()-> Unit,
    viewModel: AddDocumentViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val memoFieldMinHeight = screenHeight * 0.25f

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let{
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.addAttachment(AttachmentType.IMAGE,it.toString())
        }
    }

    val audioPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) {
        uri ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.addAttachment(AttachmentType.AUDIO, it.toString()) }

    }

    LaunchedEffect(uiState.isSaved) {
        if(uiState.isSaved){
            viewModel.clearSavedState()
            onSaved()
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text("문서 추가")},
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "뒤로가기"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            ) {
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "문서 추가",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "텍스트를 입력하거나 이미지를 첨부하면 먼저 로컬에 저장되고, AI가 나중에 정리합니다.",
                    style = MaterialTheme.typography.bodyMedium
                )

                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = viewModel::updateTitle,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("제목") }
                )

                OutlinedTextField(
                    value = uiState.originalText,
                    onValueChange = viewModel::updateOriginalText,
                    modifier = Modifier
                        .fillMaxWidth().heightIn(min = memoFieldMinHeight),
                    label = {Text("텍스트 메모")},
                    placeholder = {Text("회의 내용, 아이디어, 메모를 입력하세요")}
                )

                OutlinedButton(
                    onClick = {imagePicker.launch(arrayOf("image/*"))},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("이미지 첨부")
                }

                OutlinedButton(
                    onClick = {audioPicker.launch(arrayOf("audio/*"))},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("오디오 첨부")
                }

                uiState.attachments.forEach { attachment ->
                    Text("${attachment.type.name}: ${attachment.path}")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = viewModel::saveDocument,
                enabled = !uiState.isSaving,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(if(uiState.isSaving)"저장 중..." else "저장")
            }

            uiState.errorMessage?.let{
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium )
            }


        }


    }


}