package com.luckycart.retrofit

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
}