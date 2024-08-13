package com.example.androidrepositoriesapplication.ui.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidrepositoriesapplication.domain.domain_models.AndroidRepositoryItem
import com.example.androidrepositoriesapplication.domain.repository.AndroidDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// The Viewmodel of the Details Screen annotated as HiltViewModel and the AndroidDataRepository is
// Injected using @Inject annotation
@HiltViewModel
class DetailsViewModel @Inject constructor(private val androidDataRepository: AndroidDataRepository) : ViewModel() {
    // The details we want to show
    private val _details = MutableLiveData<AndroidRepositoryItem?>(null)
    val details: LiveData<AndroidRepositoryItem?> get() = _details

    fun getDetails(id: Long){
        viewModelScope.launch {
            // fetching the details drom the database by id
            _details.value = androidDataRepository.getAndroidRepositoryRecord(id)
        }
    }
}