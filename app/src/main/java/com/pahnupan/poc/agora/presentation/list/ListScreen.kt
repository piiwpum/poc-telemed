package com.pahnupan.poc.agora.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pahnupan.poc.agora.composable.theme.Typography
import com.pahnupan.poc.agora.domain.entries.QueueEntity
import com.pahnupan.poc.agora.list.composable.ItemQueue
import com.pahnupan.poc.agora.presentation.HeaderBar
import com.pahnupan.poc.agora.presentation.home.composable.PermissionScreen


@Composable
fun ListRoute(viewModel: ListViewModel) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value


    LaunchedEffect(true) {
        viewModel.emitIntent(ListIntent.Init)
    }

    ListScreen(
        uiState = uiState,
        onRefresh = {
            viewModel.emitIntent(ListIntent.Refresh)
        },
        onClickItem = {}
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListScreen(
    uiState: ListUiState,
    onRefresh: () -> Unit = {},
    onClickItem: () -> Unit = {}
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            HeaderBar("Queue Room", onClickLogout = {})

            Text(
                modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                text = "สวัสดีหมอ ตอนนี้มีคิวทั้งหมด ... !",
                style = Typography.titleLarge
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (uiState) {
                    ListUiState.Loading -> {
                        Text(
                            modifier = Modifier.padding(top = 24.dp),
                            text = "รอซักครู่",
                            style = Typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    ListUiState.Empty -> {
                        Text(
                            text = "ยังไม่มี queue ในตอนนี้",
                            style = Typography.bodyMedium
                        )
                        Button(onClick = onRefresh) {
                            Text("refresh")
                        }
                    }

                    ListUiState.Error -> {
                        Text(
                            text = "something wrong",
                            style = Typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onRefresh) {
                            Text("refresh")
                        }
                    }

                    is ListUiState.Success -> {
                        PullToRefreshBox(
                            isRefreshing = false,
                            onRefresh = onRefresh,
                            state = rememberPullToRefreshState(),
                        ) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(items = uiState.data, key = { it.queueId }) {
                                    ItemQueue(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        name = it.userId,
                                        queueNo = it.queueId.toIntOrNull() ?: 0,
                                        onClickItem = {}
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }


    }
}

@Preview
@Composable
private fun ListScreenViewModelSuccess() {
    ListScreen(
        uiState = ListUiState.Success(
            data = listOf(
                QueueEntity(
                    userId = "1234",
                    queueId = "1",
                    status = "sud"
                ),
                QueueEntity(
                    userId = "1234",
                    queueId = "2",
                    status = "sud"
                ),
                QueueEntity(
                    userId = "1234",
                    queueId = "3",
                    status = "sud"
                ),
                QueueEntity(
                    userId = "1234",
                    queueId = "4",
                    status = "sud"
                ),
            )
        ),
        onClickItem = {})
}

@Preview
@Composable
private fun ListScreenViewModelError() {
    ListScreen(
        uiState = ListUiState.Error
    )
}

@Preview
@Composable
private fun ListScreenViewModelLoading() {
    ListScreen(
        uiState = ListUiState.Loading
    )
}

@Preview
@Composable
private fun ListScreenViewModelEmpty() {
    ListScreen(
        uiState = ListUiState.Empty
    )
}