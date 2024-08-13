package com.example.androidrepositoriesapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidrepositoriesapplication.data.models.database_models.HeaderLinkEntity

// Data Access Object for the table "HEADER_LINK_TABLE"
@Dao
interface HeaderLinkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(data: HeaderLinkEntity)

    @Query("SELECT * FROM HEADER_LINK_TABLE WHERE id = :id")
    suspend fun getById(id: Long): HeaderLinkEntity
}