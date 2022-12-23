package com.luckycart.sdk

import com.luckycart.model.Banner
import com.luckycart.model.GameExperience

interface LuckyCartListenerCallback {
    fun onBannerListReceived(bannerList: List<Banner>)
    fun onBannerDetailReceived(banner: Banner)
    fun onPostEvent(success: String?)
    fun onGameListReceived(gameList: List<GameExperience>)
    fun onError(error: String?)
}