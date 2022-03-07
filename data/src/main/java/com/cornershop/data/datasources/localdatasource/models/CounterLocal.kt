package com.cornershop.data.datasources.localdatasource.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cornershop.data.commons.LocalDbTableNames.COUNTER_TABLE_NAME

@Entity(tableName = COUNTER_TABLE_NAME)
data class CounterLocal(
    @PrimaryKey(autoGenerate = true) val id : Int
)
