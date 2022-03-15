package com.cornershop.presentation.ui.counterList

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cornershop.domain.commons.Utils.isServiceGlobalError
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

class CounterListViewModel(
    private val counterUseCase: CounterUseCase
) : BaseViewModel(counterUseCase.getResultStatusLiveData()) {

    init {
        fetchCounterList()
    }

    lateinit var currentCounter: Counter

    private val counterListFlow = MutableStateFlow(emptyList<Counter>())
    val serviceStatusFlow: MutableStateFlow<ServiceHandler?> =
        MutableStateFlow(ServiceHandler(ServiceStatus.LOADING, ServiceCalled.ERROR))

    val counterListLiveData: LiveData<List<Counter>> = counterListFlow.asLiveData()

    // ViewLiveData
    val showLoadingLiveDada: LiveData<Boolean> =
        combine(counterListFlow, serviceStatusFlow) { _, serviceHandler ->
            serviceHandler?.status == ServiceStatus.LOADING && (serviceHandler.serviceCalled == ServiceCalled.COUNTER_GET)
        }.asLiveData()

    val showDeleteLoadingLiveDada: LiveData<Boolean> =
        combine(counterListFlow, serviceStatusFlow) { _, serviceHandler ->
            serviceHandler?.status == ServiceStatus.LOADING && (serviceHandler.serviceCalled == ServiceCalled.COUNTER_DELETE)
        }.asLiveData()

    val showEmptyViewLiveData: LiveData<Boolean> =
        combine(counterListFlow, serviceStatusFlow) { counters, serviceHandler ->
            serviceHandler?.status == ServiceStatus.SUCCESS && counters.isEmpty()
        }.asLiveData()

    val showListViewLiveData: LiveData<Boolean> =
        combine(counterListFlow, serviceStatusFlow) { counters, serviceHandler ->
            serviceHandler?.status == ServiceStatus.SUCCESS && counters.isNotEmpty()
        }.asLiveData()

    val showErrorViewLiveData: LiveData<Boolean> =
        combine(counterListFlow, serviceStatusFlow) { counters, serviceHandler ->
            (serviceHandler.let { it!!.isServiceGlobalError() }) && counters.isEmpty()
        }.asLiveData()

    val showErrorWithListViewLiveData: LiveData<Boolean> =
        combine(counterListFlow, serviceStatusFlow) { counters, serviceHandler ->
            (serviceHandler.let { it!!.isServiceGlobalError() }) && counters.isNotEmpty() && (serviceHandler?.serviceCalled != ServiceCalled.COUNTER_DELETE)
        }.asLiveData()

    val showIncOrDecErrorViewLiveData: LiveData<Boolean> =
        combine(counterListFlow, serviceStatusFlow) { _, serviceHandler ->
            (serviceHandler.let { it!!.isServiceGlobalError() }) && (serviceHandler?.serviceCalled == ServiceCalled.COUNTER_INCREMENT_TIME || serviceHandler?.serviceCalled == ServiceCalled.COUNTER_DECREMENT_TIME)
        }.asLiveData()

    val showDeleteErrorViewLiveData: LiveData<Boolean> =
        combine(counterListFlow, serviceStatusFlow) { _, serviceHandler ->
            (serviceHandler.let { it!!.isServiceGlobalError() }) && (serviceHandler?.serviceCalled == ServiceCalled.COUNTER_DELETE)
        }.asLiveData()

    // Methods
    fun fetchCounterList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val counterList = counterUseCase.getCounterList()
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

    fun incrementTime(counter: Counter) {
        currentCounter = counter

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val counterList = counterUseCase.incrementTime(counter)
                counterListFlow.value = counterList
            }
        }
    }

    fun decrementTime(counter: Counter) {
        currentCounter = counter

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val counterList = counterUseCase.decrementTime(counter)
                counterListFlow.value = counterList
            }
        }
    }
}
