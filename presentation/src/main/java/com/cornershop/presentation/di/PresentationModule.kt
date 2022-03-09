package com.cornershop.presentation.di

import com.cornershop.presentation.ui.counterList.CounterAdapter
import com.cornershop.presentation.ui.counterList.CounterListViewModel
import com.cornershop.presentation.ui.createCounter.CreateCounterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    // ViewModels
    viewModel { CounterListViewModel(get(), get()) }
    viewModel { CreateCounterViewModel(get()) }

    // Adapters
    factory { CounterAdapter() }
}
