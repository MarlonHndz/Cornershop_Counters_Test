package com.cornershop.presentation.ui.counterList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.domain.models.Counter
import com.cornershop.domain.useCases.CounterUseCase
import com.cornershop.presentation.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CounterListViewModel(
    private val counterUseCase: CounterUseCase,
    private val context: Context
) : ViewModel() {

    private val _counterList: MutableLiveData<List<Counter>> = MutableLiveData()

    val counterList: LiveData<List<Counter>>
        get() = _counterList

    fun fetchCounterList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
//                val counterList = counterUseCase.getCounterList()
                val counterList = getMockList()
                _counterList.postValue(counterList)
            }
        }
    }

    private fun getMockList(): List<Counter> {
        val entireGames: Array<String> = context.resources.getStringArray(R.array.drinks_array)
        return entireGames.map {
            Counter(
                id = 1,
                title = it,
                count = 1
            )
        }
    }
}
