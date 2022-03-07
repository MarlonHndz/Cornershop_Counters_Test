package com.cornershop.domain.di

import com.cornershop.domain.useCases.CounterUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { CounterUseCase(get()) }
}
