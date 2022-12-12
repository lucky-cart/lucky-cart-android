package com.luckycart.retrofit.cart

import com.google.gson.JsonObject
import com.luckycart.model.GameResponse
import com.luckycart.model.TransactionResponse
import com.luckycart.retrofit.ApiManager
import io.reactivex.Observable

class TransactionDataManager {

    private var transactionApiManager = ApiManager().cartApi
    fun sendCart(cart: JsonObject?): Observable<TransactionResponse> {
        return transactionApiManager.sendCart(cart)
    }

    fun getGames(key: String, cartID: String, customerId: String): Observable<GameResponse> {
        return transactionApiManager.getGames(key, cartID, customerId)
    }
}