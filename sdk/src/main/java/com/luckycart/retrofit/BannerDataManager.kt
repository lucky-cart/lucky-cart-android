package com.luckycart.retrofit

import com.luckycart.model.BannerDetails
import com.luckycart.model.Banners
import io.reactivex.Observable

class BannerDataManager {
    private var apiManager = ApiManager.instance

    fun listAvailableBanners(key: String, customerId: String): Observable<Banners> {
        return apiManager.apiService.listAvailableBanners(key, customerId)
    }

    fun getBannerDetails(
        key: String,
        customerId: String,
        pageType: String,
        pageId: String
    ): Observable<BannerDetails> {
        return apiManager.apiService.getBannerDetails(key, customerId, pageType, pageId)
    }
}