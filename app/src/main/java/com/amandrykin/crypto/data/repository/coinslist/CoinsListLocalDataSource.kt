package com.amandrykin.crypto.data.repository.coinslist

import androidx.lifecycle.LiveData
import com.amandrykin.crypto.data.local.database.CoinsDatabase
import com.amandrykin.crypto.data.local.database.CoinsListEntity
import javax.inject.Inject

class CoinsListLocalDataSource @Inject constructor(private val database: CoinsDatabase) {
    val allCoinsList: LiveData<List<CoinsListEntity>> = database.coinsListDao().coinsList()

    suspend fun insertCoinsIntoDatabase(coinsToInsert: List<CoinsListEntity>) {
        if (coinsToInsert.isNotEmpty()) {
            database.coinsListDao().insert(coinsToInsert)
        }
    }

    suspend fun favouriteSymbols(): List<String> = database.coinsListDao().favouriteSymbols()

    suspend fun updateFavouriteStatus(symbol: String): CoinsListEntity? {
        val coin = database.coinsListDao().coinFromSymbol(symbol)
        coin?.let {
            val coinsListEntity = CoinsListEntity(
                it.symbol,
                it.id,
                it.name,
                it.price,
                it.changePercent,
                it.image,
                it.isFavourite.not()
            )

            if (database.coinsListDao().updateCoinsListEntity(coinsListEntity) > 0) {
                return coinsListEntity
            }
        }
        return null
    }
}