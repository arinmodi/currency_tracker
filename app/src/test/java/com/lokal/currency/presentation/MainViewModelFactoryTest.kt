package com.lokal.currency.presentation

import com.lokal.currency.data.repository.currency.datasource.CurrencyRemoteDataSource
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelFactoryTest {

    @Mock
    private lateinit var currencyRemoteDataSource: CurrencyRemoteDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testCreate() {
        val mainViewModelFactory = MainViewModelFactory(currencyRemoteDataSource)
        val actual = mainViewModelFactory.create(MainViewModel::class.java)

        assert(actual is MainViewModel)
    }

}