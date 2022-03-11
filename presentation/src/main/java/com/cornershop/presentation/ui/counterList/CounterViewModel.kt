package com.cornershop.presentation.ui.counterList

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cornershop.domain.models.Counter
import com.cornershop.domain.repositories.serviceHandler.ServiceCalled
import com.cornershop.domain.repositories.serviceHandler.ServiceHandler
import com.cornershop.domain.repositories.serviceHandler.ServiceStatus
import com.cornershop.domain.useCases.CounterUseCase
import com.cornershop.presentation.ui.baseViews.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CounterViewModel(
    private val counterUseCase: CounterUseCase
) : BaseViewModel(counterUseCase.getResultStatusLiveData()) {

    init {
        fetchCounterList()
    }

    private val counterListFlow = MutableStateFlow(emptyList<Counter>())
    val serviceStatusFlow: MutableStateFlow<ServiceHandler?> =
        MutableStateFlow(ServiceHandler(ServiceStatus.LOADING, ServiceCalled.ERROR))

    val counterListLiveData: LiveData<List<Counter>> = counterListFlow.asLiveData()

    val showLoadingLiveDada: LiveData<Boolean> =
        combine(counterListFlow, serviceStatusFlow) { _, serviceStatus ->
            serviceStatus?.status == ServiceStatus.LOADING
        }.asLiveData()

    val showEmptyViewLiveData: LiveData<Boolean> =
        combine(counterListFlow, serviceStatusFlow) { counters, serviceStatus ->
            serviceStatus?.status == ServiceStatus.SUCCESS && counters.isEmpty()
        }.asLiveData()

    val showListViewLiveData: LiveData<Boolean> =
        combine(counterListFlow, serviceStatusFlow) { counters, serviceStatus ->
            serviceStatus?.status == ServiceStatus.SUCCESS && counters.isNotEmpty()
        }.asLiveData()

    val showErrorViewLiveData: LiveData<Boolean> =
        combine(counterListFlow, serviceStatusFlow) { counters, serviceStatus ->
            (serviceStatus?.status == ServiceStatus.ERROR_SERVICE || serviceStatus?.status == ServiceStatus.ERROR_TIMEOUT || serviceStatus?.status == ServiceStatus.ERROR_UNKNOWN_HOST) && counters.isEmpty()
        }.asLiveData()

    val showErrorWithListViewLiveData: LiveData<Boolean> =
        combine(counterListFlow, serviceStatusFlow) { counters, serviceStatus ->
            serviceStatus?.status == ServiceStatus.ERROR_SERVICE && counters.isNotEmpty()
        }.asLiveData()

    fun fetchCounterList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val counterList = counterUseCase.getCounterList()
                counterListFlow.value = counterList
            }
        }
    }

    fun saveCounter(counterName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val counterList = counterUseCase.saveCounter(counterName)
                counterListFlow.value = counterList
            }
        }
    }

    fun deleteCounterList(counters: List<Counter>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                counters.forEach { counter ->
                    val counterList = counterUseCase.deleteCounter(counter)
                    counterListFlow.value = counterList
                }
            }
        }
    }
}
