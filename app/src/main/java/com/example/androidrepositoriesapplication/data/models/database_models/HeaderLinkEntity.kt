package com.example.androidrepositoriesapplication.data.models.database_models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidrepositoriesapplication.ui.theme.TableNames

// The header link table which stores the value of the next page to fetch from the server in
// addition to the prev, first, last pages
@Entity(tableName = TableNames.HEADER_LINK_Table)
data class HeaderLinkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "prev_page") val prevPage: Int,
    @ColumnInfo(name = "next_page") val nextPage: Int,
    @ColumnInfo(name = "last_page") val lastPage: Int,
    @ColumnInfo(name = "first_page") val firstPage: Int,
)