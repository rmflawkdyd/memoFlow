package com.example.memoflow.ui.screen.home



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.memoflow.presentation.home.HomeFilter
import com.example.memoflow.presentation.home.HomeViewModel
import com.example.memoflow.ui.screen.documents.DocumentItem


@Composable
fun HomeScreen(
    onAddClick:()->Unit,
    onDocumentClick:(Long)-> Unit,
    viewModel: HomeViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "문서 추가"
                )
            }
        }
    ) { innerPadding->
        when{
            uiState.isLoading->{
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }

            uiState.documents.isEmpty()->{
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ){
                    Text("저장된 문서가 없습니다.")
                }
            }

            else ->{
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        OutlinedTextField(
                            value = uiState.searchQuery,
                            onValueChange = viewModel::updateSearchQuery,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {Text("문서 검색")},
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = null
                                )
                            },
                            singleLine = true
                        )
                    }

                    item {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
                            AssistChip(
                                onClick = {viewModel.updateFilter(HomeFilter.ALL)},
                                label = {Text("전체")}
                            )
                            AssistChip(
                                onClick = {viewModel.updateFilter(HomeFilter.PROCESSING)},
                                label = {Text("처리 중")}
                            )
                            AssistChip(
                                onClick = {viewModel.updateFilter(HomeFilter.DONE)},
                                label = {Text("완료")}
                            )
                            AssistChip(
                                onClick = {viewModel.updateFilter(HomeFilter.FAILED)},
                                label = {Text("실패")}
                            )
                        }
                    }

                    if(uiState.filteredDocuments.isEmpty()){
                        item {
                            Text("조건에 맞는 문서가 없습니다.")
                        }
                    }else{
                        items(
                            items = uiState.documents,
                            key ={it.id}
                        ){document->
                            DocumentItem(
                                document = document,
                                onClick = {onDocumentClick(document.id)}
                            )

                        }
                    }
                }
            }

        }

    }
}