package com.example.androidrepositoriesapplication.data.db

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.androidrepositoriesapplication.data.models.database_models.AndroidRepositoryEntity
import kotlinx.coroutines.flow.Flow

// Data Access Object for the table "ANDROID_REPOSITORY_TABLE"
@Dao
interface AndroidRepositoryDao {

    // This query is used to insert/update the local database with new records
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(data: List<AndroidRepositoryEntity>)

    @Query("SELECT COUNT(*) FROM ANDROID_REPOSITORY_TABLE")
    fun getRecordCountFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM ANDROID_REPOSITORY_TABLE")
    fun getRecordCount(): Int

    @Query("SELECT * FROM ANDROID_REPOSITORY_TABLE")
    fun pagingSource(): Flow<List<AndroidRepositoryEntity>?>

    @Query("DELETE FROM ANDROID_REPOSITORY_TABLE")
    suspend fun clearAll()

    @Query("SELECT * FROM ANDROID_REPOSITORY_TABLE WHERE id = :id")
    suspend fun getById(id: Long): AndroidRepositoryEntity

}