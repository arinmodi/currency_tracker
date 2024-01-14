package com.lokal.currency.data.repository.currency.datasourceImpl

import com.lokal.currency.data.api.ApiManager
import com.lokal.currency.data.model.currencyDetail.CurrencyDetail
import com.lokal.currency.data.model.currencyRate.CurrencyRate
import com.lokal.currency.data.repository.currency.datasource.CurrencyRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class CurrencyRemoteDataSourceImpl(private val apiManager: ApiManager) : CurrencyRemoteDataSource {

    override suspend fun getLive(accessKey: String): Flow<Response<CurrencyRate>> {
        return flow { emit(apiManager.getLive(accessKey)) }
    }

    override suspend fun getList(accessKey: String): Flow<Response<CurrencyDetail>> =
        flow { emit(apiManager.getList(accessKey)) }

}