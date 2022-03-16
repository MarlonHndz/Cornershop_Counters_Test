package com.cornershop.testing_commons

import com.cornershop.domain.models.Counter

class MocksFactory {
    fun <T> createObject(clazz: Class<T>): T {
        return when (clazz) {
            Counter::class.java -> getMockCounter() as T
            else -> throw IllegalArgumentException("Class nor available to mock")
        }
    }

    fun <T> createList(clazz: Class<T>, listSize: Int): List<T> {
        return when (clazz) {
            Counter::class.java -> getMockListOfCounters(listSize) as List<T>
            else -> throw java.lang.IllegalArgumentException("Class nor available to mock")
        }
    }

    private fun getMockCounter(): Counter {
        return Counter(
            id = "myId",
            title = "My Title",
            count = 1,
            isSelected = false
        )
    }

    private fun getMockListOfCounters(size: Int): List<Counter> {
        val list = ArrayList<Counter>()
        for (i in 0 until size) {
            list.add(getMockCounter())
        }
        return list
    }
}
