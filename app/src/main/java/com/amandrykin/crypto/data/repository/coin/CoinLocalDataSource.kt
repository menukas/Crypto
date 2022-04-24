package com.amandrykin.crypto.data.repository.coin

import com.amandrykin.crypto.data.local.database.CoinsDatabase
import javax.inject.Inject

class CoinLocalDataSource @Inject constructor(private val database: CoinsDatabase) {

    fun coinBySymbol(symbol: String) = database.coinsListDao().coinLiveDataFromSymbol(symbol)
}