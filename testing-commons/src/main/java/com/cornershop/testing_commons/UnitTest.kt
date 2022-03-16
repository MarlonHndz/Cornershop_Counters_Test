package com.cornershop.testing_commons

import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
abstract class UnitTest {
    protected val mocksFactory = MocksFactory()
}
