package com.cornershop.presentation.ui.baseViews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cornershop.domain.repositories.serviceHandler.Result
import com.cornershop.domain.repositories.serviceHandler.ServiceCalled
import com.cornershop.domain.repositories.serviceHandler.ServiceHandler
import com.cornershop.domain.repositories.serviceHandler.ServiceStatus
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel(
    private val resultStatusLiveData: MutableLiveData<Result<Any>>
) : ViewModel() {
    var serviceStatusHandlerLiveData: LiveData<ServiceHandler> = MutableLiveData()

    init {
        serviceStatusHandlerLiveData = Transformations.map(resultStatusLiveData) {
            when (it) {
                is Result.Loading -> ServiceHandler(ServiceStatus.LOADING, it.serviceCalled)
                is Result.Success -> ServiceHandler(ServiceStatus.SUCCESS, it.serviceCalled)
                is Result.Error -> getErrorCode(it, it.serviceCalled)
                else -> ServiceHandler(
                    ServiceStatus.ERROR_NO_INTERNET_CONNECTION,
                    ServiceCalled.ERROR
                )
            }
        }
    }

    private fun getErrorCode(
        error: Result.Error,
        serviceCalled: ServiceCalled
    ): ServiceHandler {
        return when (error.exception) {
            is SocketTimeoutException -> {
                ServiceHandler(ServiceStatus.ERROR_TIMEOUT, serviceCalled)
            }
            is UnknownHostException -> {
                ServiceHandler(ServiceStatus.ERROR_UNKNOWN_HOST, serviceCalled)
            }
            else -> ServiceHandler(ServiceStatus.ERROR_SERVICE, serviceCalled)
        }
    }
}
