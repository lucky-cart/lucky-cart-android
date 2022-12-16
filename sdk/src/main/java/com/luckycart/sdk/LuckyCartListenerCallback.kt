package com.luckycart.sdk

import com.luckycart.model.Banner
import com.luckycart.model.BannerDetails
import com.luckycart.model.Banners
import com.luckycart.model.GameExperience
import com.luckycart.model.GameResponse
import com.luckycart.model.TransactionResponse

interface LuckyCartListenerCallback {
    @Deprecated("Use onBannerListReceived instead of onRecieveListAvailableBanners")
    fun onRecieveListAvailableBanners(banners: Banners) {
        print("Use onBannerListReceived instead of onRecieveListAvailableBanners")
    }
    fun onBannerListReceived(bannerList: List<Banner>)
    @Deprecated("Use onBannerDetailReceived instead of onRecieveBannerDetails")
    fun onRecieveBannerDetails(bannerDetails: BannerDetails) {
        print("Use onBannerDetailReceived instead of onRecieveBannerDetails")
    }
    fun onBannerDetailReceived(banner: Banner)
    @Deprecated("Use onPostEvent instead of onRecieveSendCartTransactionResponse")
    fun onRecieveSendCartTransactionResponse(transactionResponse: TransactionResponse) {
        print("Use onPostEvent instead of onRecieveSendCartTransactionResponse")
    }
    fun onPostEvent(success: String?)
    @Deprecated("Use onGameListReceived instead of onRecieveListGames")
    fun onRecieveListGames(gameResponse: GameResponse) {
        print("Use onGameListReceived instead of onRecieveListGames")
    }
    fun onGameListReceived(gameList: List<GameExperience>)
    fun onError(error: String?)

}