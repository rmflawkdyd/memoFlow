package com.example.memoflow.ui.screen.documents

import android.R.attr.onClick
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.memoflow.ui.model.fakeDocuments

@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun DocumentDetailScreen(
    documentId: Long,
    onBack:()-> Unit
){
    val item = fakeDocuments.firstOrNull { it.id == documentId }

    Scaffold(
        topBar={
            CenterAlignedTopAppBar(
                title={Text(item?.title?:"문서 상세")},
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card{
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("원본 내용")
                    Text("여기에 QCR 결과, 입력 텍스트, 전사 결과가 들어갑니다.")
                }
            }
            Card{
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("AI 요약")
                    Text(item?.summary ?:"요약 없음")
                }
            }

            Card{
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("핵심 키워크")
                    Text(item?.tags?.joinToString(prefix = "#", separator = "  #") ?: "")
                }
            }

            Card{
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("액션 아이템")
                    Text("- QA 일정 확정")
                    Text("- 디자인 수정안 공유")
                    Text("- 다음 회의 일정 조율")
                }
            }
        }

    }
}