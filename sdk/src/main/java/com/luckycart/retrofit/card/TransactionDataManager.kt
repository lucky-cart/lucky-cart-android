package com.luckycart.retrofit.card


import com.google.gson.JsonObject
import com.luckycart.model.Card
import com.luckycart.model.TransactionResponse
import io.reactivex.Observable


class TransactionDataManager {
    private var transactionApiManager = TransactionApiManager.instance
    fun sendCard(card: Card): Observable<TransactionResponse> {
        return transactionApiManager.transactionService.sendCard(card)
    }

}