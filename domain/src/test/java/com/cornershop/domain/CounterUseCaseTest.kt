package com.cornershop.domain

import com.cornershop.domain.models.Counter
import com.cornershop.domain.repositories.CounterRepository
import com.cornershop.domain.useCases.CounterUseCase
import com.cornershop.testing_commons.UnitTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class CounterUseCaseTest : UnitTest() {

    @Mock
    lateinit var counterRepository: CounterRepository

    lateinit var counterUseCase: CounterUseCase

    @Before
    fun setup() {
        counterUseCase = CounterUseCase(counterRepository)
    }

    @Test
    fun `getCounterList is success`() = runBlocking {
        // Given
        val repositoryResponse = mocksFactory.createList(Counter::class.java, 2)
        Mockito.`when`(counterRepository.getCounterList()).thenReturn(repositoryResponse)

        // When
        val useCaseResponse = counterUseCase.getCounterList()

        // Then
        assert(useCaseResponse == repositoryResponse)
    }

    @Test
    fun `saveCounter is success`() = runBlocking {
        // Given
        val mockTitle = "My Title"
        val repositoryResponse = mocksFactory.createList(Counter::class.java, 2)
        Mockito.`when`(counterRepository.saveCounter(mockTitle)).thenReturn(repositoryResponse)

        // When
        val useCaseResponse = counterUseCase.saveCounter(mockTitle)

        // Then
        assert(useCaseResponse == repositoryResponse)
    }

    @Test
    fun `deleteCounter is success`() = runBlocking {
        // Given
        val mockCounter = mocksFactory.createObject(Counter::class.java)
        val repositoryResponse = mocksFactory.createList(Counter::class.java, 2)
        Mockito.`when`(counterRepository.deleteCounter(mockCounter)).thenReturn(repositoryResponse)

        // When
        val useCaseResponse = counterUseCase.deleteCounter(mockCounter)

        // Then
        assert(useCaseResponse == repositoryResponse)
    }

    @Test
    fun `incrementTime is success`() = runBlocking {
        // Given
        val mockCounter = mocksFactory.createObject(Counter::class.java)
        val repositoryResponse = mocksFactory.createList(Counter::class.java, 2)
        Mockito.`when`(counterRepository.incrementTime(mockCounter)).thenReturn(repositoryResponse)

        // When
        val useCaseResponse = counterUseCase.incrementTime(mockCounter)

        // Then
        assert(useCaseResponse == repositoryResponse)
    }

    @Test
    fun `decrementTime is success`() = runBlocking {
        // Given
        val mockCounter = mocksFactory.createObject(Counter::class.java)
        val repositoryResponse = mocksFactory.createList(Counter::class.java, 2)
        Mockito.`when`(counterRepository.decrementTime(mockCounter)).thenReturn(repositoryResponse)

        // When
        val useCaseResponse = counterUseCase.decrementTime(mockCounter)

        // Then
        assert(useCaseResponse == repositoryResponse)
    }
}
