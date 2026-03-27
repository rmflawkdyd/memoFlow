package com.example.memoflow.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack:()->Unit,
){
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
            Card{
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text("AI 처리 방식")
                    RadioButton(selected = true, onClick = {})
                    Text("자동")
                    RadioButton(selected = false, onClick = {})
                    Text("온디바이스 우선")
                    RadioButton(selected = false, onClick = {})
                    Text("클라우드 우선")
                }
            }

            Card{
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("데이터 사용")
                    Text("Wi-Fi에서만 AI 처리")
                }
            }
        }
    }
}