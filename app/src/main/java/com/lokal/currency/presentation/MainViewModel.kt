package com.lokal.currency.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokal.currency.data.model.currancy.CurrencyList
import com.lokal.currency.data.repository.currency.datasource.CurrencyRemoteDataSource
import androidx.lifecycle.viewModelScope
import com.lokal.currency.data.model.currancy.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class MainViewModel(
    private val currencyRemoteDataSource: CurrencyRemoteDataSource
) : ViewModel() {
    private val accessKey = "4e76250f071e289ee462ca6fe3ba9670"

    val data: MutableLiveData<CurrencyList> = MutableLiveData()
    val dataFailure: MutableLiveData<Boolean> = MutableLiveData()

    fun getCryptoList() {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRemoteDataSource.getLive(accessKey).zip(
                currencyRemoteDataSource.getList(accessKey)
            ) { rates, details ->
                val currencyRates = rates.body()?.rates!!
                val currencyDetails = details.body()?.crypto!!
                val currencyList = CurrencyList()
                for (currency in currencyDetails.keys) {
                    if (currencyRates.contains(currency)) {
                        currencyList.add(
                            Currency(
                                currencyDetails[currency]?.name_full!!,
                                currencyDetails[currency]?.icon_url!!,
                                currencyRates[currency]!!
                            )
                        )
                    }
                }
                return@zip currencyList
            }.flowOn(Dispatchers.IO).catch { e ->
                dataFailure.postValue(true)
            }.collect {
                data.postValue(it)
            }
        }
    }
}