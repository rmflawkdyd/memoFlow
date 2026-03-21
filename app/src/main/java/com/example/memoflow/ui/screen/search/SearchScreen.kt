package com.example.memoflow.ui.screen.search

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.memoflow.ui.component.DocumentCard
import com.example.memoflow.ui.model.fakeDocuments

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBack:()->Unit,
    onOpenDetail:(Long)->Unit,
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("검섹") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        }
    ) {innerPadding->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item{
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("문서 검색") },
                    leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxSize()
                    )
            }

            item{
                Text("최근 검색")
            }

            item { Text("• 회의") }
            item { Text("• 일정") }
            item { Text("• OCR") }

            item {
                Text("검색 결과")
            }

            items(fakeDocuments){item->
                DocumentCard(
                    item = item,
                    onClick = { onOpenDetail(item.id) }
                )

            }
        }

    }
}