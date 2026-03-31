package com.example.memoflow.ui.screen.documents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.memoflow.domain.model.Document
import com.example.memoflow.domain.model.DocumentStatus

@Composable
fun DocumentItem(
    document: Document,
    onClick:()-> Unit,
    onDeleteClick:()-> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
    ){
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = document.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "문서 삭제"
                    )
                }
            }

            Text(
                text = document.title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = when(document.status){
                    DocumentStatus.PROCESSING -> "처리 중"
                    DocumentStatus.DONE -> "완료"
                    DocumentStatus.FAILED -> "실패"
                },
                style = MaterialTheme.typography.labelMedium

            )

            when(document.status){
                DocumentStatus.PROCESSING->{
                    Text(
                        text = if(document.originalText.isBlank()){
                            "이미지가 저장되었습니다. OCR 및 AI 처리를 기다리는 중입니다."
                        }else {
                            "문서가 저장되었습니다. AI가 내용을 정리하는 중입니다."
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                DocumentStatus.DONE -> {
                    Text(
                        text = document.summary ?: document.originalText,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                DocumentStatus.FAILED -> {
                    Text(
                        text = document.errorMessages ?: "AI 처리에 실패했습니다.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
