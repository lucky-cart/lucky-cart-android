package com.luckycart.sdk

import com.luckycart.model.BannerDetails
import com.luckycart.model.Banners

interface LuckyCartListenerCallback {
    fun listAvailableBanners(banners: Banners)
    fun getBannerDetails(banners: BannerDetails)

}