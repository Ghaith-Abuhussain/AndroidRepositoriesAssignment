package com.example.androidrepositoriesapplication.ui.repositories.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadType
import com.example.androidrepositoriesapplication.common.LoadingType
import com.example.androidrepositoriesapplication.common.OrderingOptions
import com.example.androidrepositoriesapplication.domain.repository.AndroidDataRepository
import com.example.androidrepositoriesapplication.ui.theme.RECORD_COUNT_PER_PAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(private val androidDataRepository: AndroidDataRepository) :
    ViewModel() {
    // true when start refreshing and false otherwise
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    // The current android repository records in the DB
    private val repositoriesPagingFlow = androidDataRepository.getAndroidRepositoryList()

    // This variable is used in the RepositoriesScreen to use it in the Scrolling LaunchedEffect to
    // compare with the last visible item in the list to detect when to call for an append
    private val _recordsCountInDb = androidDataRepository.getRecordsCountFlow()
    val recordsCountInDb: Flow<Int> get() = _recordsCountInDb

    // true when start appending and false otherwise and this variable is used to display the loading
    // indicator at the end of the Android Repository list when start appending data from the server
    private val _isAppending = MutableStateFlow(false)
    val isAppending: StateFlow<Boolean> get() = _isAppending

    // To show the error
    private val _error = MutableStateFlow(Error(false, ""))
    val error: StateFlow<Error> get() = _error

    // The actual text from search text field
    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> get() = _searchText

    // The type of android repository ordering (NO_ORDER, ASCENDING, DESCENDING) changed by the
    // Radio Group in the RepositoriesScreen
    private val _orderingType = MutableStateFlow(OrderingOptions.NO_ORDER)
    val orderingType: StateFlow<OrderingOptions> get() = _orderingType

    // Used in the landscape mode to indicates the lastly selected item in the Android
    // Repository Screen
    private val _lastSelectedId = MutableStateFlow<Long>(-1)
    val lastSelectedId: StateFlow<Long> get() = _lastSelectedId

    // Refresh the List from the remote server
    suspend fun refresh() {
        viewModelScope.launch {
            try {
                _error.value = Error(false, "")
                _isRefreshing.value = true
                androidDataRepository.refreshDataFromRemoteSource(
                )
                _isRefreshing.value = false
            } catch (e: Exception) {
                _error.value = Error(true, e.message!!)
                _isRefreshing.value = false
            }
        }
    }

    // Append new data to the list from the remote server
    suspend fun append() {
        viewModelScope.launch {
            try {
                _error.value = Error(false, "")
                _isAppending.value = true
                androidDataRepository.appendDataFromRemoteSource()
                _isAppending.value = false
            } catch (e: Exception) {
                _error.value = Error(true, e.message!!)
                _isAppending.value = false
            }

        }

    }

    // change the last selected index (in the landscape mode)
    fun onChangeLastIdx(idx: Long) {
        _lastSelectedId.value = idx
    }

    // Order Logic
    // by combining the state flow of the orderingType and the value of the repositoriesPagingFlow
    // we produce a new flow according to the orderingType
    private val androidRepositoriesAfterOrdering = orderingType
        .combine(repositoriesPagingFlow) { orderingType, repositories ->
            when (orderingType) {
                OrderingOptions.NO_ORDER -> {
                    repositories
                }
                OrderingOptions.ASCENDING -> {
                    repositories!!.sortedBy {
                        it.stargazersCount
                    }
                }
                else -> {
                    repositories!!.sortedByDescending {
                        it.stargazersCount
                    }
                }
            }
        }

    // Called when the ordering type is changed by the Ordering Radio Group
    fun onChangeOrderingType(type: OrderingOptions) {
        _orderingType.value = type
    }

    // Searching Logic
    // by combining the state flow of the searchText and the value of the androidRepositoriesAfterOrdering
    // we produce a new flow according to the searchText (We keep the items that meet the search condition.)
    // Note that the searching logic is applied after the androidRepositoriesAfterOrdering logic and
    // the  order between the two logics are not importand
    @OptIn(FlowPreview::class)
    val androidRepositoriesAfterSearch = searchText
        .debounce(500L)
        .combine(androidRepositoriesAfterOrdering) { text, repositories ->
            if (text.isEmpty()) {
                repositories
            } else {
                repositories!!.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }

    // Change the search text value
    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }
}