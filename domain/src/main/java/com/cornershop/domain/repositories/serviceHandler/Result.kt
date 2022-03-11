package com.cornershop.domain.repositories.serviceHandler

open class Result<out T : Any> {
    class Loading(val serviceCalled: ServiceCalled) : Result<Nothing>()

    data class Success<out T : Any>(
        val data: T,
        val serviceCalled: ServiceCalled
    ) : Result<T>()

    data class Error(
        val exception: Exception,
        val code: Int? = null,
        val serviceCalled: ServiceCalled
    ) : Result<Nothing>()
}
