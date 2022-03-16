package com.cornershop.testing_commons

import com.cornershop.data.datasources.localdatasource.models.CounterLocal
import com.cornershop.data.datasources.remotedatasource.model.CounterResponse
import retrofit2.Response

class AndroidMocksFactory(
    private val mocksFactory: MocksFactory
) {
    fun <T> createObject(clazz: Class<T>): T {
        return when (clazz) {
            CounterResponse::class.java -> getMockCounterResponse() as T
            CounterLocal::class.java -> getMockCounterLocal() as T
            else -> mocksFactory.createObject(clazz)
        }
    }

    fun <T> createList(clazz: Class<T>, listSize: Int): List<T> {
        return when (clazz) {
            CounterResponse::class.java -> getMockListOfCountersResponse(listSize) as List<T>
            CounterLocal::class.java -> getMockListOfCountersLocal(listSize) as List<T>
            else -> mocksFactory.createList(clazz, listSize)
        }
    }

    fun <T> createServiceResponse(clazz: Class<T>, listSize: Int): Response<List<CounterResponse>> {
        return when (clazz) {
            Response::class.java -> getMockServiceResponse(listSize)
            else -> throw IllegalArgumentException("Class nor available to mock")
        }
    }

    private fun getMockCounterResponse(): CounterResponse {
        return CounterResponse(
            id = "myId",
            title = "My Title",
            count = 1
        )
    }

    private fun getMockListOfCountersResponse(size: Int): List<CounterResponse> {
        val list = ArrayList<CounterResponse>()
        for (i in 0 until size) {
            list.add(getMockCounterResponse())
        }
        return list
    }

    private fun getMockCounterLocal(): CounterLocal {
        return CounterLocal(
            id = "myId",
            title = "My Title",
            count = 1
        )
    }

    private fun getMockListOfCountersLocal(size: Int): List<CounterLocal> {
        val list = ArrayList<CounterLocal>()
        for (i in 0 until size) {
            list.add(getMockCounterLocal())
        }
        return list
    }

    private fun getMockServiceResponse(size: Int): Response<List<CounterResponse>> {
        val listBody = getMockListOfCountersResponse(size)
        return Response.success(listBody)
    }
}
