package com.example.memoflow.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.memoflow.ui.model.fakeDocuments
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import com.example.memoflow.ui.component.DocumentCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onGoDocument:()-> Unit,
    onOpenDetail:(Long)-> Unit,
    onGoSearch:()-> Unit,
    onGoSettings:()-> Unit
){
    Scaffold(
        topBar={
            CenterAlignedTopAppBar(
                title = { Text("MemoFlow") },
                actions = {
                    IconButton(onClick=onGoSearch) {
                        Icon(Icons.Outlined.Search, contentDescription = "검색")
                    }
                    IconButton(onClick=onGoSettings) {
                        Icon(Icons.Outlined.Settings, contentDescription = "설정")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}){
                Icon(Icons.Outlined.Description, contentDescription = "추가")
            }
        }
    ) { innerPadding->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            ){
            item{
                TextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxSize(),
                    placeholder = {Text("문서, 태그, 요약 검색")},
                    leadingIcon = {
                        Icon(Icons.Outlined.Search, contentDescription = null)
                    },
                    readOnly = true
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("빠른 시작")
                    Button(onClick = {}) {
                        Text("텍스트 메모 작성")
                    }
                    OutlinedButton(onClick = {}) {
                        Text("이미지 문서 스캔")
                    }
                    OutlinedButton(onClick = {}) {
                        Text("파일 업로드")
                    }
                }
            }

            item {
                Text("최근 문서")
            }

            items(fakeDocuments){item->
                DocumentCard(
                    item = item,
                    onClick = {onOpenDetail(item.id)}
                )
            }

            item {
                OutlinedButton(onClick = onGoDocument) {
                    Text("전체 문서 보기")
                }
            }
        }
    }
}