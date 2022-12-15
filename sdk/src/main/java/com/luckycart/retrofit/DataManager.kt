package com.luckycart.retrofit

import com.google.gson.JsonObject
import com.luckycart.model.BannerDetails
import com.luckycart.model.BannerResponse
import com.luckycart.model.Banners
import com.luckycart.model.Event
import com.luckycart.model.GameExperience
import com.luckycart.model.GameFilter
import com.luckycart.model.GameResponse
import com.luckycart.model.TransactionResponse
import io.reactivex.Observable

class DataManager {

    private var bannerApi = ApiManager().bannerApi
    private var transactionApiManager = ApiManager().cartApi
    private var shopperApi = ApiManager().shopperApi
    private var displayerApi = ApiManager().displayerApi
    private var gamesApi = ApiManager().gamesApi

    fun listAvailableBanners(key: String, customerId: String): Observable<Banners> {
        return bannerApi.listAvailableBanners(key, customerId)
    }

    fun getBannerDetails(
        key: String, customerId: String, pageType: String, pageId: String
    ): Observable<BannerDetails> {
        return bannerApi.getBannerDetails(key, customerId, pageType, pageId)
    }

    fun sendCart(cart: JsonObject?): Observable<TransactionResponse> {
        return transactionApiManager.sendCart(cart)
    }

    fun getGames(key: String, cartID: String, customerId: String): Observable<GameResponse> {
        return transactionApiManager.getGames(key, cartID, customerId)
    }

    fun sendShopperEvent(event: Event): Observable<Void> {
        return shopperApi.sendShopperEvent(event)
    }

    fun getGamesAccess(
        siteKey: String,
        shopperId: String,
        count: Int,
        filters: GameFilter
    ): Observable<List<GameExperience>> {
        return gamesApi.getGamesAccess(siteKey, shopperId, count, filters)
    }

    fun getBannerExperience(
        auth_key: String,
        customerId: String,
        type: String?,
        platform: String?,
        page_type: String,
        format: String,
        pageId: String?,
        store: String?,
        store_type: String?
    ): Observable<BannerResponse> {
        return displayerApi.getBannerExperience(
            auth_key,
            customerId,
            type,
            platform,
            page_type,
            format,
            pageId,
            store,
            store_type
        )
    }
}