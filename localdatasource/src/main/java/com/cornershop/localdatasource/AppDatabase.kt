package com.cornershop.localdatasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cornershop.data.datasources.localdatasource.models.CounterLocal
import com.cornershop.localdatasource.daos.CounterDao

@Database(
    entities = [CounterLocal::class],
    version = AppDatabase.DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun CounterDao(): CounterDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()

        private var INSTANCE: AppDatabase? = null
        private const val DATABASE_NAME = "cornershop_database"
        const val DATABASE_VERSION = 1
    }
}
