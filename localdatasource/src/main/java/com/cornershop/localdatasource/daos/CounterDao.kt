package com.cornershop.localdatasource.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.cornershop.data.datasources.localdatasource.models.CounterLocal

@Dao
interface CounterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(counters: List<CounterLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(counter: CounterLocal)

    @Update
    fun updateCounter(counter: CounterLocal)

    @Delete
    fun delete(counter: CounterLocal)

    @Query("DELETE FROM Counter")
    fun deleteAll()

    @Query("SELECT * FROM counter")
    fun getAll(): List<CounterLocal>

    @Query("SELECT * FROM counter WHERE id = :id LIMIT 1")
    fun getCounterByID(id: String): CounterLocal

    @Transaction
    fun deleteAllAndInsert(counterLocalList: List<CounterLocal>) {
        deleteAll()
        insertAll(counterLocalList)
    }
}
