package com.example.androidrepositoriesapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.RemoteMediator
import androidx.paging.liveData
import androidx.paging.map
import androidx.room.withTransaction
import com.example.androidrepositoriesapplication.common.Link
import com.example.androidrepositoriesapplication.common.LoadingType
import com.example.androidrepositoriesapplication.common.parseRFC5988LinkHeader
import com.example.androidrepositoriesapplication.data.api.ApiService
import com.example.androidrepositoriesapplication.data.db.AppDatabase
import com.example.androidrepositoriesapplication.data.models.database_models.AndroidRepositoryEntity
import com.example.androidrepositoriesapplication.data.models.database_models.HeaderLinkEntity
import com.example.androidrepositoriesapplication.data.models.mappers.toAndroidRepositoryEntity
import com.example.androidrepositoriesapplication.data.models.mappers.toAndroidRepositoryItem
import com.example.androidrepositoriesapplication.domain.domain_models.AndroidRepositoryItem
import com.example.androidrepositoriesapplication.domain.repository.AndroidDataRepository
import com.example.androidrepositoriesapplication.ui.theme.RECORD_COUNT_PER_PAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

// This is the app main repository that is responsible of many type of local database, network operations
class AndroidDataRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val apiService: ApiService,
) : AndroidDataRepository {

    // This function is responsible of getting the List of data stored in the database as flow
    override fun getAndroidRepositoryList(): Flow<List<AndroidRepositoryItem>?> {
        return appDatabase.androidRepositoryDao.pagingSource().map { listOfrecords ->
            listOfrecords?.map {
                it.toAndroidRepositoryItem()
            }
        }
    }

    // This function is responsible of refreshing the data or getting the first page from the server
    override suspend fun refreshDataFromRemoteSource(
    ) {
        try {
            // Calling for the first page
            val apiResponse = apiService.getNewAndroidRepositoriesPage(
                searchType = "android",
                pageIdx = 1,
                perPage = RECORD_COUNT_PER_PAGE
            )

            when (apiResponse.code()) {
                // HTTP.ok
                200 -> {
                    // Extract the link header and save the last, next, prev, first pages in the local db
                    saveLastLinkHeaderDb(parseRFC5988LinkHeader(apiResponse.headers()["Link"] as String))

                    // save the records in db with transaction (The transaction will be marked as successful unless an exception is thrown in the suspending block)
                    appDatabase.withTransaction {
                        appDatabase.androidRepositoryDao.clearAll()
                        val androidRepositories = apiResponse.body()?.items

                        val androidrRepositoryEntities =
                            androidRepositories!!.map { it.toAndroidRepositoryEntity() }
                        appDatabase.androidRepositoryDao.upsertAll(androidrRepositoryEntities)
                    }
                }
                // Do nothing in case of error
                403 -> {
                    return
                }
                422 -> {
                    return
                }
            }
        } catch (e: IOException) {
            throw e
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }


    }

    // This function is responsible for appending new page from the server depending on the next page value stored in the DB
    override suspend fun appendDataFromRemoteSource(
    ) {
        try {
            // Get the db record wher the next page value is stored
            val linkDb = getSavedLinkHeaderDb()
            // if next page value = -1 then the last page is reached and nothing to append
            if(linkDb.nextPage == -1)
                return

            // call for the next page
            val apiResponse = apiService.getNewAndroidRepositoriesPage(
                searchType = "android",
                pageIdx = linkDb.nextPage,
                perPage = RECORD_COUNT_PER_PAGE
            )

            when (apiResponse.code()) {
                // Http.OK
                200 -> {
                    // Extract the link header and save the last, next, prev, first pages in the local db
                    saveLastLinkHeaderDb(parseRFC5988LinkHeader(apiResponse.headers()["Link"] as String))

                    // save the records in db with transaction (The transaction will be marked as successful unless an exception is thrown in the suspending block)
                    appDatabase.withTransaction {
                        val androidRepositories = apiResponse.body()?.items

                        val androidrRepositoryEntities =
                            androidRepositories!!.map { it.toAndroidRepositoryEntity() }
                        appDatabase.androidRepositoryDao.upsertAll(androidrRepositoryEntities)
                    }
                }
                // Do nothing in case of error
                403 -> {
                    return
                }
                422 -> {
                    return
                }
            }
        } catch (e: IOException) {
            throw e
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }


    }

    // This function is used in the Details Display to get the repository details that must be showm from the db
    override suspend fun getAndroidRepositoryRecord(id: Long): AndroidRepositoryItem {
        val localAndroidRepositoryRecord = appDatabase.androidRepositoryDao.getById(id)
        return localAndroidRepositoryRecord.toAndroidRepositoryItem()
    }

    // This function is used in the splash screen to know if the db is empty or not
    override fun getRecordCount(): Int {
        return appDatabase.androidRepositoryDao.getRecordCount()
    }

    // This function is used in the RepositoriesScreen to use it in the Scrolling LaunchedEffect to
    // compare with the last visible item in the list to detect when to call for an append
    override fun getRecordsCountFlow(): Flow<Int> {
        return appDatabase.androidRepositoryDao.getRecordCountFlow()
    }

    // Save the last Header link in the DB
    private suspend fun saveLastLinkHeaderDb(linkeHeader: List<Link>) {
        val prevPage = linkeHeader.find { link ->
            link.rel == "prev"
        }?.pageNumber ?: -1
        val nextPage = linkeHeader.find { link ->
            link.rel == "next"
        }?.pageNumber ?: -1
        val lastPage = linkeHeader.find { link ->
            link.rel == "last"
        }?.pageNumber ?: -1
        val firstPage = linkeHeader.find { link ->
            link.rel == "first"
        }?.pageNumber ?: -1

        appDatabase.headerLinkDao.insertRecord(
            HeaderLinkEntity(
                id = 1,
                prevPage = prevPage,
                nextPage = nextPage,
                lastPage = lastPage,
                firstPage = firstPage
            )
        )
    }

    // Get the Saved Header Link from the db
    private suspend fun getSavedLinkHeaderDb(): HeaderLinkEntity {
        return appDatabase.headerLinkDao.getById(1)
    }

    // Clear the Android Repositories table in the db after each refresh.
    override suspend fun clearDb() {
        appDatabase.androidRepositoryDao.clearAll()
    }
}