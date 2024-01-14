package com.lokal.currency.data.api

import com.lokal.currency.data.model.currencyDetail.CurrencyDetail
import com.lokal.currency.data.model.currencyRate.CurrencyRate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiManager {

    @GET("/live?")
    suspend fun getLive(
        @Query("access_key") accessKey : String
    ) : Response<CurrencyRate>

    @GET("/list?")
    suspend fun getList(
        @Query("access_key") accessKey : String
    ) : Response<CurrencyDetail>

}