package com.cornershop.data.di

import com.cornershop.data.repositories.CounterRepositoryImpl
import com.cornershop.domain.repositories.CounterRepository
import org.koin.dsl.module

val dataModule = module {
    // Repositories
    factory<CounterRepository> { CounterRepositoryImpl(get(), get()) }

    // Mappers
}
