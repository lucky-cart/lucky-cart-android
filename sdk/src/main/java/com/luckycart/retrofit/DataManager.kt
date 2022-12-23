package com.luckycart.retrofit

import com.luckycart.model.BannerResponse
import com.luckycart.model.Event
import com.luckycart.model.GameExperience
import com.luckycart.model.GameFilter
import io.reactivex.Observable
import retrofit2.Response

class DataManager {

    private var shopperApi = ApiManager().shopperApi
    private var displayerApi = ApiManager().displayerApi
    private var gamesApi = ApiManager().gamesApi

    fun sendShopperEvent(event: Event): Observable<Response<Void>> {
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