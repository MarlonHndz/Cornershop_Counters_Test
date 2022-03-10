package com.cornershop.presentation.ui.counterList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.domain.models.Counter
import com.cornershop.domain.useCases.CounterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CounterViewModel(
    private val counterUseCase: CounterUseCase
) : ViewModel() {

    private val _counterList: MutableLiveData<List<Counter>> = MutableLiveData()

    val counterList: LiveData<List<Counter>>
        get() = _counterList

    fun fetchCounterList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val counterList = counterUseCase.getCounterList()
                _counterList.postValue(counterList)
            }
        }
    }

    fun saveCounter(counterName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val counterList = counterUseCase.saveCounter(counterName)
                _counterList.postValue(counterList)
            }
        }
    }

    fun deleteCounterList(counters: List<Counter>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                counters.forEach { counter ->
                    val counterList = counterUseCase.deleteCounter(counter)
                    _counterList.postValue(counterList)
                }
            }
        }
    }
}
