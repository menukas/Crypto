package com.amandrykin.crypto.api

import com.amandrykin.crypto.api.model.Coin
import com.amandrykin.crypto.api.model.HistoryPriceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("coins/markets")
    suspend fun coinList(@Query("vs_currency") targetCurrency: String): Response<List<Coin>>

    @GET("coins/{id}/market_chart")
    suspend fun historicalPrice(
        @Path("id") id: String,
        @Query("vs_currency") targetCurrency: String,
        @Query("days") days: Int
    ): Response<HistoryPriceResponse>
}