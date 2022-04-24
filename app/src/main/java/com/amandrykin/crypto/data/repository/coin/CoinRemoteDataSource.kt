package com.amandrykin.crypto.data.repository.coin

import com.amandrykin.crypto.api.ApiInterface
import com.amandrykin.crypto.api.BaseRemoteDataSource
import com.amandrykin.crypto.api.Result
import com.amandrykin.crypto.api.model.HistoryPriceResponse
import javax.inject.Inject

class CoinRemoteDataSource @Inject constructor(private val service: ApiInterface): BaseRemoteDataSource() {

    suspend fun historyPrice(symbol: String, targetCur: String, days: Int = 30): Result<HistoryPriceResponse> =
        getResult {
            service.historicalPrice(symbol, targetCur, days)
        }
}