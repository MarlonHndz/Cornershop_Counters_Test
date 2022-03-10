package com.cornershop.data.mappers

import com.cornershop.data.datasources.localdatasource.models.CounterLocal
import com.cornershop.domain.models.Counter

class CounterLocalToCounterMapper {

    operator fun invoke(counterLocalList: List<CounterLocal>): List<Counter> {
        return counterLocalList.map { counterLocal ->
            Counter(
                id = counterLocal.id,
                title = counterLocal.title,
                count = counterLocal.count
            )
        }
    }
}
