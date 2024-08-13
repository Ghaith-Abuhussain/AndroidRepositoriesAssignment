package com.example.androidrepositoriesapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidrepositoriesapplication.data.models.database_models.AndroidRepositoryEntity
import com.example.androidrepositoriesapplication.data.models.database_models.HeaderLinkEntity

// Here is the class that represents the app database with its entities and Dao's
@Database(
    entities = [AndroidRepositoryEntity::class, HeaderLinkEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val androidRepositoryDao: AndroidRepositoryDao
    abstract val headerLinkDao: HeaderLinkDao
}