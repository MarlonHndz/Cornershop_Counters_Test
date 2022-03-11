package com.cornershop.domain.useCases

import androidx.lifecycle.MutableLiveData
import com.cornershop.domain.repositories.serviceHandler.Result

abstract class BaseUseCase {
    abstract fun getResultStatusLiveData(): MutableLiveData<Result<Any>>
}
