package com.lokal.currency.data.repository.currency.datasource

import com.lokal.currency.data.model.currencyDetail.CurrencyDetail
import com.lokal.currency.data.model.currencyRate.CurrencyRate
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CurrencyRemoteDataSource {

    suspend fun getLive(
        accessKey : String
    ) : Flow<Response<CurrencyRate>>

    suspend fun getList(
        accessKey : String
    ) : Flow<Response<CurrencyDetail>>

}