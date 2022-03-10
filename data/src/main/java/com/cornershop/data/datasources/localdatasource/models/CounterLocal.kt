package com.cornershop.data.datasources.localdatasource.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cornershop.data.commons.LocalDbTableNames.COUNTER_TABLE_NAME

@Entity(tableName = COUNTER_TABLE_NAME)
data class CounterLocal(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "count") val count: Int,
)
