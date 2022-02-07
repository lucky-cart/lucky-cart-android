package com.luckycart.sdk

import com.luckycart.model.BannerDetails
import com.luckycart.model.Banners
import com.luckycart.model.TransactionResponse

interface LuckyCartListenerCallback {
    fun listAvailableBanners(banners: Banners)
    fun getBannerDetails(banners: BannerDetails)
    fun sendCard(transactionResponse: TransactionResponse)

}