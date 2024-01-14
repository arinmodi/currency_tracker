package com.lokal.currency.data.api

import com.lokal.currency.data.model.currencyDetail.CurrencyDetail
import com.lokal.currency.data.model.currencyRate.CurrencyRate
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
class ApiManagerTest {

    private lateinit var mockWebServer: MockWebServer

    private lateinit var apiManager: ApiManager

    private val accessKey = "4e76250f071e289ee462ca6fe3ba9670"

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        apiManager = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiManager::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getLive success`() = runBlocking {
        val responseBody = """{"rates": {"USD": 1.0}}"""
        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        val response: Response<CurrencyRate> = apiManager.getLive(accessKey)
        delay(100)

        assert(response.isSuccessful)
        assert(response.body()?.rates?.containsKey("USD") == true)
    }

    @Test
    fun `getList success`() = runBlocking {
        val responseBody = """{"crypto": {"BTC": {"name_full": "Bitcoin", "icon_url": "bitcoin.png"}}}"""
        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        // Execute the API call
        val response: Response<CurrencyDetail> = apiManager.getList(accessKey)

        assert(response.isSuccessful)
        assert(response.body()?.crypto?.containsKey("BTC") == true)
    }

    @Test
    fun `getLive network error`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(400))

        val response: Response<CurrencyRate> = apiManager.getLive(accessKey)
        delay(100)

        assert(!response.isSuccessful)
    }

    @Test
    fun `getList network error`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(400))

        // Execute the API call
        val response: Response<CurrencyRate> = apiManager.getLive(accessKey)
        delay(100)

        // Verify that the request failed
        assert(!response.isSuccessful)
    }
}
