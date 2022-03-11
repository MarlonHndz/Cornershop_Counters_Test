package com.cornershop.data.repositories

import androidx.lifecycle.MutableLiveData
import com.cornershop.domain.repositories.serviceHandler.Result
import com.cornershop.domain.repositories.serviceHandler.ServiceCalled
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository {

    private val resultStatusLiveData = MutableLiveData<Result<Any>>()

    suspend fun <T : Any> makeApiCall(
        call: suspend () -> Response<T>,
        saveCallResultLocally: suspend (T) -> Unit,
        serviceCalled: ServiceCalled,
        silentLoading: Boolean = true
    ): Result<T> {
        // Start Loading
        var result: Result<T> = Result.Loading(serviceCalled)
        if (!silentLoading) resultStatusLiveData.postValue(result)

        // Start call invoke
        result = safeApiCall(call, serviceCalled)
        val data: T?

        // Posting Result
        when (result) {
            is Result.Success -> {
                data = result.data
                saveCallResultLocally(data)
                resultStatusLiveData.postValue(result)
            }
            is Result.Error -> {
                resultStatusLiveData.postValue(result)
            }
        }
        return result
    }

    private suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        serviceCalled: ServiceCalled
    ): Result<T> {
        return try {
            val response = call.invoke()

            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!, serviceCalled)
            } else {
                Result.Error(IOException(response.message()), response.code(), serviceCalled)
            }
        } catch (socketTimeOutError: SocketTimeoutException) {
            Result.Error(socketTimeOutError, serviceCalled = serviceCalled)
        } catch (unknownHostError: UnknownHostException) {
            Result.Error(unknownHostError, serviceCalled = serviceCalled)
        } catch (e: Throwable) {
            Result.Error(IOException(e.message), serviceCalled = serviceCalled)
        }
    }

    open fun getResultStatus() = resultStatusLiveData
}
