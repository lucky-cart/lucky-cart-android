package com.luckycart.sdk

import com.luckycart.model.BannerDetails
import com.luckycart.model.BannerResponse
import com.luckycart.model.Banners
import com.luckycart.model.GameResponse
import com.luckycart.model.TransactionResponse

interface LuckyCartListenerCallback {

    fun onRecieveListAvailableBanners(banners: Banners)
    fun onRecieveListAvailableBanners(bannerResponse: BannerResponse)
    fun onRecieveBannerDetails(bannerDetails: BannerDetails)
    fun onRecieveSendCartTransactionResponse(transactionResponse: TransactionResponse)
    fun onRecieveListGames(gameResponse: GameResponse)
    fun onPostEvent(success: String?)
    fun onError(error: String?)

}