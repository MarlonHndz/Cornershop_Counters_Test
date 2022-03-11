package com.cornershop.presentation.di

import com.cornershop.presentation.ui.baseViews.BaseViewModel
import com.cornershop.presentation.ui.counterList.CounterAdapter
import com.cornershop.presentation.ui.counterList.CounterViewModel
import com.cornershop.presentation.ui.createCounter.CreateCounterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    // ViewModels
    viewModel { BaseViewModel(get()) }
    viewModel { CounterViewModel(get()) }
    viewModel { CreateCounterViewModel(get()) }

    // Adapters
    factory { CounterAdapter() }
}
