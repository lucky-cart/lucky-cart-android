package com.luckycart.sdk

import com.luckycart.model.BannerDetails
import com.luckycart.model.Banners
import com.luckycart.model.GameResponse
import com.luckycart.model.TransactionResponse

interface LuckyCartListenerCallback {

    fun listAvailableBanners(banners: Banners)
    fun getBannerDetails(banners: BannerDetails)
    fun sendCart(transactionResponse: TransactionResponse)
    fun getGame(game: GameResponse)
    fun onError(error: String?)

}