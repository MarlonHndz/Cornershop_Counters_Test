package com.cornershop.domain.repositories.serviceHandler

data class ServiceHandler(
    val status: ServiceStatus,
    val serviceCalled: ServiceCalled
)

enum class ServiceStatus {
    LOADING,
    SUCCESS,
    ERROR_TIMEOUT,
    ERROR_UNKNOWN_HOST,
    ERROR_SERVICE,
    ERROR_NO_INTERNET_CONNECTION
}

enum class ServiceCalled {
    ERROR,
    COUNTER_GET,
    COUNTER_DELETE,
    COUNTER_SAVE
}
