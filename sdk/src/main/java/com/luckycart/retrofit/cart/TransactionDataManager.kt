package com.luckycart.retrofit.cart

import com.google.gson.JsonObject
import com.luckycart.model.GameResponse
import com.luckycart.model.TransactionResponse
import io.reactivex.Observable

class TransactionDataManager {

    private var transactionApiManager = TransactionApiManager.instance
    fun sendCart(cart: JsonObject?): Observable<TransactionResponse> {
        return transactionApiManager.transactionService.sendCart(cart)
    }

    fun getGames(key: String, cartID: String, customerId: String): Observable<GameResponse> {
        return transactionApiManager.transactionService.getGames(key, cartID, customerId)
    }
}