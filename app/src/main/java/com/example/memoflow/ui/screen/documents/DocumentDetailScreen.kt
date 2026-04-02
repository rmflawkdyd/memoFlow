package com.example.memoflow.ui.screen.documents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.memoflow.domain.model.DocumentStatus
import com.example.memoflow.presentation.documents.detail.DetailViewModel

@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun DocumentDetailScreen(
    onBack:()-> Unit,
    viewModel: DetailViewModel= hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val document = uiState.document

    Scaffold(
        topBar={
            CenterAlignedTopAppBar(
                title={Text("문서 상세")},
                navigationIcon = {
                    IconButton(onClick = onBack){
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        }
    ) {innerPadding->
        when{
            uiState.isLoading->{
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            document == null->{
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ){
                    Text("문서를 찾을 수 없습니다.")
                }
            }
            else ->{
                Column(
                    modifier = Modifier.fillMaxSize().padding(innerPadding)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = document.title,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    // 상태 텍스트 아래에 추가
                    when (document.status) {

                        DocumentStatus.QUEUED -> {
                            Text(
                                text = "문서가 저장되었습니다. 처리를 준비 중입니다.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        }

                        DocumentStatus.WAITING_FOR_WIFI -> {
                            Text(
                                text = "Wi-Fi 연결 후 AI 처리가 시작됩니다.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        }
                        DocumentStatus.PROCESSING -> {
                            Text(
                                text = "문서를 분석하고 있습니다.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        }

                        DocumentStatus.DONE -> {

                        }

                        DocumentStatus.FAILED -> {
                            Text(
                                text = document.errorMessages ?: "문서 처리 중 오류가 발생했습니다.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )

                            Button(
                                onClick = viewModel::retryAiProcessing
                            ) {
                                Text("다시 시도")
                            }
                        }
                    }



                    SectionTitle("원문")
                    Text(document.originalText.ifBlank { "이미지에서 추출될 텍스트를 기다리는 중입니다." })

                    SectionTitle("요약")
                    Text(document.summary ?: "아직 요약되지 않았습니다.")

                    SectionTitle("키워드")
                    Text(document.keywords ?: "아직 키워드가 없습니다.")

                    SectionTitle("해야 할 일")
                    Text(document.actionItems ?: "아직 액션 아이템이 없습니다.")

                    document.errorMessages?.let{
                        SectionTitle("오류")
                        Text(it)
                    }

                }
            }
        }

    }
}

@Composable
private fun SectionTitle(title:String){
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium
    )
}
