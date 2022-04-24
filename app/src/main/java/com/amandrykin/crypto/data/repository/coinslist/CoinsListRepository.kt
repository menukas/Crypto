package com.amandrykin.crypto.data.repository.coinslist

import com.amandrykin.crypto.api.Result
import com.amandrykin.crypto.api.successed
import com.amandrykin.crypto.api.utils.Constants
import com.amandrykin.crypto.data.local.database.CoinsListEntity
import com.amandrykin.crypto.data.local.preferences.SharedPreferencesStorage
import java.util.*
import javax.inject.Inject

class CoinsListRepository @Inject constructor(
    private val coinsListRemoteDataSource: CoinsListRemoteDataSource,
    private val coinsListLocalDataSource: CoinsListLocalDataSource,
    private val sharedPreferencesStorage: SharedPreferencesStorage
    ) {
    val allCoinsList = coinsListLocalDataSource.allCoinsList

    suspend fun coinsList(targetCur: String) {
        when (val result = coinsListRemoteDataSource.coinsList(targetCur)) {
            is Result.Success -> {
                if (result.successed) {
                    val favSymbols = coinsListLocalDataSource.favouriteSymbols()

                    val customList = result.data.let {
                        it.filter { item -> item.symbol.isNullOrEmpty().not() }
                            .map { item ->
                                CoinsListEntity(
                                    item.symbol ?: "",
                                    item.id,
                                    item.name,
                                    item.price,
                                    item.changePercent,
                                    item.image,
                                    favSymbols.contains(item.symbol)
                                )
                            }
                    }
                    coinsListLocalDataSource.insertCoinsIntoDatabase(customList)
                    sharedPreferencesStorage.timeLoadedAt = Date().time
                    Result.Success(true)

                } else {
                    Result.Error(Constants.GENERIC_ERROR)
                }
            }
            else -> result as Result.Error
        }
    }

    suspend fun updateFavouriteStatus(symbol: String): Result<CoinsListEntity> {
        val result = coinsListLocalDataSource.updateFavouriteStatus(symbol)
        return result?.let {
            Result.Success(it)
        } ?: Result.Error(Constants.GENERIC_ERROR)
    }

    fun loadData(): Boolean {
        val lastLoad = sharedPreferencesStorage.timeLoadedAt
        val currentTime = Date().time
        return currentTime - lastLoad > 15 * 1000
    }
}