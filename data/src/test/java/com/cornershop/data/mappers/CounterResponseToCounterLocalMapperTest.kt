package com.cornershop.data.mappers

import com.cornershop.data.datasources.localdatasource.models.CounterLocal
import com.cornershop.data.datasources.remotedatasource.model.CounterResponse
import com.cornershop.testing_commons.AndroidUnitTest
import org.junit.Before
import org.junit.Test

class CounterResponseToCounterLocalMapperTest : AndroidUnitTest() {
    lateinit var counterResponseToCounterLocalMapper: CounterResponseToCounterLocalMapper

    @Before
    fun setup() {
        counterResponseToCounterLocalMapper = CounterResponseToCounterLocalMapper()
    }

    @Test
    fun `Mapper invoked`() {
        // Given
        val listSize = 2
        val counterResponse = androidMocksFactory.createList(CounterResponse::class.java, listSize)

        // When
        val mappedCounter = counterResponseToCounterLocalMapper(counterResponse)

        // Then
        val expectedMapped = getExpectedMapped()
        assert(mappedCounter.size == expectedMapped.size)
        for (counter in mappedCounter) {
            assert(counter.title == "My Title")
        }
    }

    private fun getExpectedMapped(): List<CounterLocal> {
        return listOf(
            CounterLocal(
                id = "myId1",
                title = "My Title",
                count = 1
            ),
            CounterLocal(
                id = "myId2",
                title = "My Title",
                count = 1
            )
        )
    }
}
