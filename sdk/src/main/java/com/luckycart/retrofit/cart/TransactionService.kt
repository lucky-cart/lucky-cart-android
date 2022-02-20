package com.luckycart.retrofit.cart

import com.google.gson.JsonObject
import com.luckycart.model.GameResponse
import com.luckycart.model.TransactionResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TransactionService {

    @POST("cart/ticket.json")
    fun sendCart(@Body cart: JsonObject?): Observable<TransactionResponse>

    @GET("cart/games")
    fun getGames(
        @Query("authKey") authKey: String,
        @Query("cartId") cartId: String,
        @Query("customerId") customerId: String
    ): Observable<GameResponse>
}