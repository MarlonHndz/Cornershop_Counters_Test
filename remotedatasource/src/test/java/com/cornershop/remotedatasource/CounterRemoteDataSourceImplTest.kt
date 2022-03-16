package com.cornershop.remotedatasource

import com.cornershop.domain.models.Counter
import com.cornershop.remotedatasource.api.services.CounterService
import com.cornershop.remotedatasource.datasources.CounterRemoteDataSourceImpl
import com.cornershop.testing_commons.AndroidUnitTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class CounterRemoteDataSourceImplTest : AndroidUnitTest() {

    @Mock
    lateinit var counterService: CounterService

    private lateinit var counterRemoteDataSourceImpl: CounterRemoteDataSourceImpl

    @Before
    fun setup() {
        counterRemoteDataSourceImpl = CounterRemoteDataSourceImpl(counterService)
    }

    @Test
    fun `getCounterResponseList is Success`() = runBlocking {
        // Given
        val serviceResponse = androidMocksFactory.createServiceResponse(Response::class.java, 2)
        `when`(counterService.getCounterResponseList()).thenReturn(serviceResponse)

        // When
        val datasourceResponse = counterRemoteDataSourceImpl.getCounterResponseList()

        // Then
        assert(datasourceResponse == serviceResponse)
    }

    @Test
    fun `saveCounter is Success`() = runBlocking {
        // Given
        val mockTitle = "My Title"
        val serviceResponse = androidMocksFactory.createServiceResponse(Response::class.java, 2)
        `when`(counterService.saveCounter(mockTitle)).thenReturn(serviceResponse)

        // When
        val datasourceResponse = counterRemoteDataSourceImpl.saveCounter(mockTitle)

        // Then
        assert(datasourceResponse == serviceResponse)
    }

    @Test
    fun `deleteCounter is Success`() = runBlocking {
        // Given
        val mockCounter = androidMocksFactory.createObject(Counter::class.java)
        val serviceResponse = androidMocksFactory.createServiceResponse(Response::class.java, 2)
        `when`(counterService.deleteCounter(mockCounter.id)).thenReturn(serviceResponse)

        // When
        val datasourceResponse = counterRemoteDataSourceImpl.deleteCounter(mockCounter)

        // Then
        assert(datasourceResponse == serviceResponse)
    }

    @Test
    fun `incrementTime is Success`() = runBlocking {
        // Given
        val mockCounter = androidMocksFactory.createObject(Counter::class.java)
        val serviceResponse = androidMocksFactory.createServiceResponse(Response::class.java, 2)
        `when`(counterService.incrementTime(mockCounter.id)).thenReturn(serviceResponse)

        // When
        val datasourceResponse = counterRemoteDataSourceImpl.incrementTime(mockCounter)

        // Then
        assert(datasourceResponse == serviceResponse)
    }

    @Test
    fun `decrementTime is Success`() = runBlocking {
        // Given
        val mockCounter = androidMocksFactory.createObject(Counter::class.java)
        val serviceResponse = androidMocksFactory.createServiceResponse(Response::class.java, 2)
        `when`(counterService.decrementTime(mockCounter.id)).thenReturn(serviceResponse)

        // When
        val datasourceResponse = counterRemoteDataSourceImpl.decrementTime(mockCounter)

        // Then
        assert(datasourceResponse == serviceResponse)
    }
}
