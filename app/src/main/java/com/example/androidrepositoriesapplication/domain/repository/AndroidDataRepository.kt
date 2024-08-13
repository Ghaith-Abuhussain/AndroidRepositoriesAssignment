package com.example.androidrepositoriesapplication.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.LoadType
import androidx.paging.PagingData
import com.example.androidrepositoriesapplication.common.Link
import com.example.androidrepositoriesapplication.common.LoadingType
import com.example.androidrepositoriesapplication.data.models.database_models.HeaderLinkEntity
import com.example.androidrepositoriesapplication.domain.domain_models.AndroidRepositoryItem
import kotlinx.coroutines.flow.Flow

// Interface for the AndroidDataRepository
interface AndroidDataRepository {
    fun getAndroidRepositoryList():  Flow<List<AndroidRepositoryItem>?>
    suspend fun refreshDataFromRemoteSource()
    suspend fun appendDataFromRemoteSource()
    suspend fun getAndroidRepositoryRecord(id: Long): AndroidRepositoryItem
    fun getRecordCount(): Int
    fun getRecordsCountFlow(): Flow<Int>
    suspend fun clearDb()
}