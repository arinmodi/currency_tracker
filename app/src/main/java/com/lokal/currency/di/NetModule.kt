package com.lokal.currency.di

import android.app.Application
import com.google.gson.GsonBuilder
import com.lokal.currency.data.api.ApiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        val client = OkHttpClient.Builder().apply {
            this.addNetworkInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
        }.build()

        return Retrofit.Builder()
            .baseUrl("http://api.coinlayer.com/")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().disableHtmlEscaping().setExclusionStrategies()
                        .create()
                )
            )
            .build()

    }

    @Singleton
    @Provides
    fun provideApiManger(retrofit: Retrofit): ApiManager {
        return retrofit.create(ApiManager::class.java)
    }

}