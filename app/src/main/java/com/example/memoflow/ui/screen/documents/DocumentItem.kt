package com.example.memoflow.ui.screen.documents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.memoflow.data.local.entity.DocumentStatus
import com.example.memoflow.domain.model.Document

@Composable
fun DocumentItem(
    document: Document,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = document.title)

            Spacer(modifier = Modifier.height(8.dp))

            when(document.status){
                DocumentStatus.PROCESSING -> {
                    Text("AI가 문서를 분석 중입니다.")
                }

                DocumentStatus.DONE -> {
                    Text(text = document.summary ?: "요약 없음")
                }

                DocumentStatus.FAILED -> {
                    Text(text = "처리에 실패했습니다.")
                    document.errorMessages?.let {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = it)
                    }
                }
            }
        }
    }
}