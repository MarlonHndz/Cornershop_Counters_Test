package com.cornershop.testing_commons

abstract class AndroidUnitTest : UnitTest() {
    protected val androidMocksFactory = AndroidMocksFactory(mocksFactory)
}
