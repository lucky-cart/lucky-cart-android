package com.luckycart.sdk

import com.luckycart.model.Banners

interface LuckyCartListenerCallback {
    fun listAvailableBanners(banners: Banners)
}