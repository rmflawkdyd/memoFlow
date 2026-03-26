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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.memoflow.presentation.search.SearchViewModel
import com.example.memoflow.ui.component.DocumentCard
import com.example.memoflow.ui.model.fakeDocuments
import com.example.memoflow.ui.screen.documents.DocumentItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBack:()->Unit,
    onOpenDetail:(Long)->Unit,
    viewModel: SearchViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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

            if(uiState.results.isEmpty()){
                item{
                    Text("검섹 결과가 없습니다.")
                }
            }else{
                items(
                    items = uiState.results,
                    key = {it.id}
                ){ document->
                    DocumentItem(
                        document = document,
                        onClick = {onOpenDetail(document.id)}
                    )
                }
            }


        }

    }
}