package com.example.memoflow.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.memoflow.domain.settings.AiMode
import com.example.memoflow.domain.settings.OcrLanguage
import com.example.memoflow.presentation.settings.SettingsViewModel
import com.example.memoflow.ui.component.SettingsRadioRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack:()->Unit,
    viewModel: SettingsViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("설정") },
                navigationIcon = {
                    IconButton(onClick = onBack){
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        }
    ) {innerPadding->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
//            Card{
//                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
//                    Text("AI 처리 방식")
//                    SettingsRadioRow(
//                        label = "자동",
//                        selected = uiState.aiMode == AiMode.AUTO,
//                        onClick = { viewModel.updateAiMode(AiMode.AUTO) },
//                    )
//
//                    SettingsRadioRow(
//                        label = "온디바이스 우선",
//                        selected = uiState.aiMode == AiMode.ON_DEVICE_FIRST,
//                        onClick = { viewModel.updateAiMode(AiMode.ON_DEVICE_FIRST) },
//                    )
//
//                    SettingsRadioRow(
//                        label = "클라우드 우선",
//                        selected = uiState.aiMode == AiMode.CLOUD_FIRST,
//                        onClick = { viewModel.updateAiMode(AiMode.CLOUD_FIRST) },
//                    )
//
//                }
//            }

            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("OCR 언어")

                    SettingsRadioRow(
                        label = "자동(기기 언어)",
                        selected = uiState.ocrLanguage == OcrLanguage.AUTO,
                        onClick = { viewModel.updateOcrLanguage(OcrLanguage.AUTO) }
                    )

                    SettingsRadioRow(
                        label = "한국어",
                        selected = uiState.ocrLanguage == OcrLanguage.KOREAN,
                        onClick = { viewModel.updateOcrLanguage(OcrLanguage.KOREAN) }
                    )

                    SettingsRadioRow(
                        label = "일본어",
                        selected = uiState.ocrLanguage == OcrLanguage.JAPANESE,
                        onClick = { viewModel.updateOcrLanguage(OcrLanguage.JAPANESE) }
                    )

                    SettingsRadioRow(
                        label = "중국어",
                        selected = uiState.ocrLanguage == OcrLanguage.CHINESE,
                        onClick = { viewModel.updateOcrLanguage(OcrLanguage.CHINESE) }
                    )

                    SettingsRadioRow(
                        label = "라틴 문자",
                        selected = uiState.ocrLanguage == OcrLanguage.LATIN,
                        onClick = { viewModel.updateOcrLanguage(OcrLanguage.LATIN) }
                    )
                }
            }


            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("데이터 사용")

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                            .selectable(
                                selected = uiState.wifiOnly,
                                onClick = { viewModel.updateWifiOnly(!uiState.wifiOnly) },
                                role = Role.Switch
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Wi-Fi에서만 AI 처리")

                        Switch(
                            checked = uiState.wifiOnly,
                            onCheckedChange = null
                        )
                    }
                }
            }

        }
    }
}