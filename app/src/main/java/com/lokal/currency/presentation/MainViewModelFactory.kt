package com.lokal.currency.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lokal.currency.data.repository.currency.datasource.CurrencyRemoteDataSource

class MainViewModelFactory(
    private val currencyRemoteDataSource: CurrencyRemoteDataSource
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            currencyRemoteDataSource
        ) as T
    }
}