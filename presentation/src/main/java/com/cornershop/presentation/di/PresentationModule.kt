package com.cornershop.presentation.di

import com.cornershop.presentation.ui.counterList.CounterAdapter
import com.cornershop.presentation.ui.counterList.CounterListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    // ViewModels
    viewModel { CounterListViewModel(get(), get()) }

    // Adapters
    factory { CounterAdapter() }
}
