package com.luckycart.retrofit

import com.luckycart.model.BannerDetails
import com.luckycart.model.Banners
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{auth_key}/{customerId}/banners/mobile/list")
    fun listAvailableBanners(
        @Path("auth_key") auth_key: String,
        @Path("customerId") customerId: String
    ): Observable<Banners>
    @GET("{auth_key}/{customerId}/banner/mobile/{page_type}/{page_id}")
    fun getBannerDetails(
        @Path("auth_key") auth_key: String,
        @Path("customerId") customerId: String,
        @Path("page_type") page_type: String,
        @Path("page_id") page_id: String
    ): Observable<BannerDetails>
}