package com.lokal.currency.di

import com.lokal.currency.presentation.MainViewModelFactory
import com.lokal.currency.data.repository.currency.datasource.CurrencyRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideMainViewModelFactory(currencyRemoteDataSource: CurrencyRemoteDataSource)
        : MainViewModelFactory {
        return MainViewModelFactory(currencyRemoteDataSource)
    }

}