package com.cornershop.data.di

import com.cornershop.data.mappers.CounterLocalToCounterMapper
import com.cornershop.data.mappers.CounterResponseToCounterLocalMapper
import com.cornershop.data.repositories.CounterRepositoryImpl
import com.cornershop.domain.repositories.CounterRepository
import org.koin.dsl.module

val dataModule = module {
    // Repositories
    factory<CounterRepository> { CounterRepositoryImpl(get(), get()) }

    // Mappers
    factory { CounterResponseToCounterLocalMapper() }
    factory { CounterLocalToCounterMapper() }
}
