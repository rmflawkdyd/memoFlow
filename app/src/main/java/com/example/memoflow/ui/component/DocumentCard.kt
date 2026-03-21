package com.example.memoflow.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.memoflow.ui.model.DocumentType
import com.example.memoflow.ui.model.DocumentUiModel
import com.example.memoflow.ui.model.ProcessingStatus

@Composable
fun DocumentCard(
    item: DocumentUiModel,
    modifier:Modifier = Modifier,
    onClick: (() -> Unit)? = null,
){
    Card(
        modifier= modifier.fillMaxWidth(),
        onClick = { onClick?.invoke() }
    ){
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
                val icon = when(item.type){
                    DocumentType.TEXT -> Icons.AutoMirrored.Outlined.Article
                    DocumentType.IMAGE -> Icons.Outlined.Image
                    DocumentType.AUDIO -> Icons.Outlined.Mic
                }
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                )
                Column {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Text(
                        text = item.createdAt,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Text(
                text = item.summary,
                style = MaterialTheme.typography.bodyMedium
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item.tags.take(3).forEach {tag->
                    AssistChip(
                        onClick = {},
                        label = { Text("#$tag") }
                    )
                }
            }

            val statusText = when(item.status){
                ProcessingStatus.PROCESSING->"처리중"
                ProcessingStatus.DONE -> "완료"
                ProcessingStatus.FAILED -> "실패"
            }

            Text(
                text = "상태: $statusText",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}