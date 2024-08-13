package com.example.androidrepositoriesapplication.data.models.database_models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.androidrepositoriesapplication.ui.theme.TableNames

// This is the class that represents owr data base table with the name "ANDROID_REPOSITORY_Table"

@Entity(tableName = TableNames.ANDROID_REPOSITORY_Table, indices = [Index(value = ["remote_record_id"],
    unique = true)])
data class AndroidRepositoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "remote_record_id") val remoteRecordId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "owner_login") val ownerLogin: String,
    @ColumnInfo(name = "stargazers_count") val stargazersCount: Int,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "updated_at") val updatedAt: String,
    @ColumnInfo(name = "html_url") val htmlUrl: String,
)