package com.amandrykin.crypto.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CoinsListDao {

    @Query("SELECT * FROM coins_list")
    fun coinsList(): LiveData<List<CoinsListEntity>>

    @Query("SELECT * FROM coins_list WHERE symbol = :symbol")
    suspend fun coinFromSymbol(symbol: String): CoinsListEntity?

    @Query("SELECT * FROM coins_list WHERE symbol = :symbol")
    fun coinLiveDataFromSymbol(symbol: String): List<CoinsListEntity>

    @Query("SELECT * FROM coins_list WHERE isFavourite = 1")
    fun favouriteCoins(): LiveData<List<CoinsListEntity>>

    @Query("SELECT symbol FROM coins_list WHERE isFavourite = 1")
    suspend fun favouriteFromSymbol(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<CoinsListEntity>)

    @Update
    suspend fun updateCoinsListEntity(data: CoinsListEntity): Int

    @Query("DELETE FROM coins_list")
    suspend fun deleteAll()
}