package com.luckycart.retrofit.card

import com.luckycart.model.Card
import com.luckycart.model.GameResponse
import com.luckycart.model.TransactionResponse
import io.reactivex.Observable

class TransactionDataManager {

    private var transactionApiManager = TransactionApiManager.instance
    fun sendCard(card: Card): Observable<TransactionResponse> {
        return transactionApiManager.transactionService.sendCard(card)
    }

    fun getGames(key: String, cardId: String, customerId: String): Observable<GameResponse> {
        return transactionApiManager.transactionService.getGames(key, cardId, customerId)
    }

}