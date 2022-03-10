package com.cornershop.domain.repositories

import com.cornershop.domain.models.Counter

interface CounterRepository {
    suspend fun getCounterList(): List<Counter>
    suspend fun saveCounter(title: String): List<Counter>
    suspend fun deleteCounter(counter: Counter): List<Counter>
}
