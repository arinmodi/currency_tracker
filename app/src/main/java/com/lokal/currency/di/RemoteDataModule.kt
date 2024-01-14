package com.lokal.currency.di

import com.lokal.currency.data.api.ApiManager
import com.lokal.currency.data.repository.currency.datasource.CurrencyRemoteDataSource
import com.lokal.currency.data.repository.currency.datasourceImpl.CurrencyRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideCurrencyRemoteDataSource(apiManager: ApiManager) : CurrencyRemoteDataSource {
        return CurrencyRemoteDataSourceImpl(apiManager)
    }

}