package com.luckycart.retrofit.card

import com.luckycart.model.Card

import com.luckycart.model.TransactionResponse
import io.reactivex.Observable
import retrofit2.http.*

interface TransactionService {
    @POST("cart/ticket.json")
    fun sendCard(@Body card : Card): Observable<TransactionResponse>
}