package com.cornershop.data.mappers

import com.cornershop.data.datasources.localdatasource.models.CounterLocal
import com.cornershop.data.datasources.remotedatasource.model.CounterResponse

class CounterResponseToCounterLocalMapper {

    operator fun invoke(counterResponseList: List<CounterResponse>): List<CounterLocal> {
        return counterResponseList.map { counterResponse ->
            CounterLocal(
                id = counterResponse.id,
                title = counterResponse.title,
                count = counterResponse.count
            )
        }
    }
}
