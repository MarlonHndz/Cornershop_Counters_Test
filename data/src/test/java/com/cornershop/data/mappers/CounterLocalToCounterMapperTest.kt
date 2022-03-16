package com.cornershop.data.mappers

import com.cornershop.data.datasources.localdatasource.models.CounterLocal
import com.cornershop.domain.models.Counter
import com.cornershop.testing_commons.AndroidUnitTest
import org.junit.Before
import org.junit.Test

class CounterLocalToCounterMapperTest : AndroidUnitTest() {
    lateinit var counterLocalToCounterMapper: CounterLocalToCounterMapper

    @Before
    fun setup() {
        counterLocalToCounterMapper = CounterLocalToCounterMapper()
    }

    @Test
    fun `Mapper invoked`() {
        // Given
        val listSize = 2
        val counterLocal = androidMocksFactory.createList(CounterLocal::class.java, listSize)

        // When
        val mappedCounter = counterLocalToCounterMapper(counterLocal)

        // Then
        val expectedMapped = getExpectedMapped()
        assert(mappedCounter.size == expectedMapped.size)
        for (counter in mappedCounter) {
            assert(counter.title == "My Title")
        }
    }

    private fun getExpectedMapped(): List<Counter> {
        return listOf(
            Counter(
                id = "myId1",
                title = "My Title",
                count = 1,
                isSelected = false
            ),
            Counter(
                id = "myId2",
                title = "My Title",
                count = 1,
                isSelected = false
            )
        )
    }
}
