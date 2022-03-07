package com.cornershop.domain.repositories

import com.cornershop.domain.models.Counter

interface CounterRepository {
    suspend fun getCounterList(): List<Counter>
}
