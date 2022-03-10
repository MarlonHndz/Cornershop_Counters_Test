package com.cornershop.presentation.ui.createCounter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.domain.useCases.CounterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateCounterViewModel(
    private val counterUseCase: CounterUseCase
) : ViewModel() {

    fun saveCounter(counterName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                counterUseCase.saveCounter(counterName)
            }
        }
    }
}
