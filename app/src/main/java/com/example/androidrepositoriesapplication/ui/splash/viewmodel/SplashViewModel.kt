package com.example.androidrepositoriesapplication.ui.splash.viewmodel

import androidx.lifecycle.ViewModel
import com.example.androidrepositoriesapplication.common.LoadingType
import com.example.androidrepositoriesapplication.domain.repository.AndroidDataRepository
import com.example.androidrepositoriesapplication.ui.repositories.viewmodel.Error
import com.example.androidrepositoriesapplication.ui.theme.RECORD_COUNT_PER_PAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// The splash viewmodel which depends on AndroidDataRepository
@HiltViewModel
class SplashViewModel @Inject constructor(private val androidDataRepository: AndroidDataRepository) : ViewModel() {

    // This variable will not change until the data fetching from the server is done with error or not
    private val _isSplashShown = MutableStateFlow(true)
    val isSplashShown: StateFlow<Boolean> get() = _isSplashShown

    private val customScope = CoroutineScope(Dispatchers.IO)

    // to start fetching the data from the server (the first page)
    suspend fun initialization() {
        customScope.launch {
            try {
                _isSplashShown.value = true
                val recordCountDb = androidDataRepository.getRecordCount()
                if(recordCountDb == 0) {
                    androidDataRepository.refreshDataFromRemoteSource()
                }
                delay(6000)
                _isSplashShown.value = false
            } catch (e: Exception) {
                delay(6000)
                _isSplashShown.value = false
            }
        }
    }
}