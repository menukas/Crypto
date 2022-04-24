package com.amandrykin.crypto.data.repository.coinslist

import com.amandrykin.crypto.api.ApiInterface
import com.amandrykin.crypto.api.BaseRemoteDataSource
import com.amandrykin.crypto.api.Result
import com.amandrykin.crypto.api.model.Coin
import javax.inject.Inject

class CoinsListRemoteDataSource @Inject constructor(private val service: ApiInterface) :
    BaseRemoteDataSource() {
    suspend fun coinsList(targetCur: String): Result<List<Coin>> =
        getResult {
            service.coinList(targetCur)
        }
}