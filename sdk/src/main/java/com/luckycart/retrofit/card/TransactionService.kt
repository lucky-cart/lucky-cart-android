package com.luckycart.retrofit.card

import com.luckycart.model.Card
import com.luckycart.model.GameResponse
import com.luckycart.model.TransactionResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TransactionService {

    @POST("cart/ticket.json")
    fun sendCard(@Body card: Card): Observable<TransactionResponse>

    @GET("cart/games")
    fun getGames(
        @Query("authKey") authKey: String,
        @Query("cartId") cartId: String,
        @Query("customerId") customerId: String
    ): Observable<GameResponse>
}