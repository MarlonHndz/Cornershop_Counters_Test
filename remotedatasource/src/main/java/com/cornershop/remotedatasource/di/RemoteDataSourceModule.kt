package com.cornershop.remotedatasource.di

import com.cornershop.data.datasources.remotedatasource.CounterRemoteDataSource
import com.cornershop.remotedatasource.api.RetrofitClient
import com.cornershop.remotedatasource.api.services.CounterService
import com.cornershop.remotedatasource.datasources.CounterRemoteDataSourceImpl
import org.koin.dsl.module

val remoteDataSourceModule = module {
    // APIs
    single { RetrofitClient.create(CounterService::class.java) }

    // DataSources
    factory<CounterRemoteDataSource> { CounterRemoteDataSourceImpl(get()) }
}
