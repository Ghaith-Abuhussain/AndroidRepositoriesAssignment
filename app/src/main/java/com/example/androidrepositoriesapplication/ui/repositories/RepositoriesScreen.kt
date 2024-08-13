package com.example.androidrepositoriesapplication.ui.repositories

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.androidrepositoriesapplication.ui.repositories.composables.EmptyRecordsComposable
import com.example.androidrepositoriesapplication.ui.repositories.composables.OrderingRadioGroup
import com.example.androidrepositoriesapplication.ui.repositories.composables.RepositoryCard
import com.example.androidrepositoriesapplication.ui.repositories.viewmodel.RepositoriesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RepositoriesScreen(
    viewModel: RepositoriesViewModel,
    onClickCard: (Long) -> Unit //This function is used to pass the selected repository id to the Navigation Host to know which details to display
) {
    val androidRepositories = viewModel.androidRepositoriesAfterSearch.collectAsState(initial = null)
    val pullToRefreshState = rememberPullToRefreshState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val isAppending by viewModel.isAppending.collectAsState()
    val error by viewModel.error.collectAsState()
    val recordsCountInDb = viewModel.recordsCountInDb.collectAsState(initial = 0)
    val searchText by viewModel.searchText.collectAsState()
    val orderingType by viewModel.orderingType.collectAsState()
    val lastSelectedId by viewModel.lastSelectedId.collectAsState()
    val scope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()

    val context = LocalContext.current

    // We want to apply the selected logic only when we are in the Landscape orientation
    val applySelectedLogic = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    // This LaunchedEffect will be triggered every time scrolling occurs and by comparing the last
    // visible index with the last record index in DB we know when to call the append function
    // from the viewmodel
    LaunchedEffect(lazyListState.isScrollInProgress) {
        lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()
        val lastVisibleItemIndex =
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
        if (lastVisibleItemIndex + 1 == recordsCountInDb.value) {
            if(!isAppending)
                viewModel.append()
        }
    }

    // Show Toast when error occurs
    LaunchedEffect(error.isError) {
        if (error.isError) {
            Toast.makeText(context, error.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    // if it is a Portrait mode no item in the list need to be selected
    LaunchedEffect(applySelectedLogic) {
        if(!applySelectedLogic) {
            viewModel.onChangeLastIdx(-1)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // The Search TextField
            TextField(
                value = searchText,
                onValueChange = viewModel::onSearchTextChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 20.dp),
                placeholder = {
                    Text(
                        text = "Search"
                    )
                },
            )
            // The radio group that is responsible of the ordering of the item in the list
            OrderingRadioGroup(orderingType = orderingType, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp), onClick = {
                    newOrdering ->
                viewModel.onChangeOrderingType(newOrdering)
            })
            if(androidRepositories.value != null) {
                if (androidRepositories.value!!.isEmpty()) {
                    if (searchText.isEmpty()) {
                        EmptyRecordsComposable(modifier = Modifier.fillMaxSize(), onClick = {
                            scope.launch {
                                viewModel.refresh()
                            }
                        })
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                            .background(color = MaterialTheme.colorScheme.background),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        state = lazyListState
                    ) {
                        items(androidRepositories.value!!.size) { idx ->
                            RepositoryCard(
                                isSelected = androidRepositories.value!![idx].id == lastSelectedId,
                                androidRepositoryItem = androidRepositories.value!![idx],
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItemPlacement(
                                        animationSpec = tween(durationMillis = 500)
                                    ),
                                onClick = {
                                    // we want to apply the selected logic in Landscape Mode Only
                                    if(applySelectedLogic) {
                                        // if the item on the list is already selected we want to unselect it and make sure that there is no details selected
                                        if(lastSelectedId == androidRepositories.value!![idx].id) {
                                            onClickCard(-1) // to make sure that the app in the landscape mode will show no details
                                            viewModel.onChangeLastIdx(-1) // to unselect the previously selected item
                                        } else {
                                            onClickCard(androidRepositories.value!![idx].id) // in the landscape mode show the selected details
                                            viewModel.onChangeLastIdx(androidRepositories.value!![idx].id) // to select the previously unselected item from the list
                                        }
                                    } else {
                                        // show the details with the id {androidRepositories.value!![idx].id} in the portrait mode
                                        onClickCard(androidRepositories.value!![idx].id)
                                    }


                                }
                            )
                        }

                        // Show CircularProgressIndicator() when appending new data from the server
                        if (isAppending) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }

                }
            }
        }

        // Call refresh function from the viewmodel when pullToRefreshState start refreshing
        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                scope.launch {
                    viewModel.refresh()
                }
            }
        }

        // Update the pullToRefreshState according to isRefresh (Stop the refreshing Indicator when isRefreshing = false)
        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                pullToRefreshState.startRefresh()
            } else {
                pullToRefreshState.endRefresh()
            }
        }

        // The PullToRefreshContainer Composable
        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter),
        )
    }
}