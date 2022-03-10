package com.cornershop.localdatasource.di

import com.cornershop.data.datasources.localdatasource.CounterLocalDataSource
import com.cornershop.localdatasource.AppDatabase
import com.cornershop.localdatasource.datasource.CounterLocalDataSourceImpl
import org.koin.dsl.module

val localDataSourceModule = module {
    // Database
    single { AppDatabase.getInstance(get()) }
    single { get<AppDatabase>().CounterDao() }

    // DataSources
    factory<CounterLocalDataSource> { CounterLocalDataSourceImpl(get(), get(), get()) }
}
