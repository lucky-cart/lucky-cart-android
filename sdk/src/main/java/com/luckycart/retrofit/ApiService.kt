package com.luckycart.retrofit

import com.luckycart.model.BannerDetails
import com.luckycart.model.BannerResponse
import com.luckycart.model.Banners
import com.luckycart.model.Event
import com.luckycart.model.GameFilter
import com.luckycart.model.GameResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("{auth_key}/{customerId}/banners/mobile/list")
    fun listAvailableBanners(
        @Path("auth_key") auth_key: String, @Path("customerId") customerId: String
    ): Observable<Banners>

    @GET("{auth_key}/{customerId}/banner/mobile/{page_type}/{page_id}")
    fun getBannerDetails(
        @Path("auth_key") auth_key: String,
        @Path("customerId") customerId: String,
        @Path("page_type") page_type: String,
        @Path("page_id") page_id: String
    ): Observable<BannerDetails>


    @POST("/v1/event")
    fun sendShopperEvent(@Body event: Event): Observable<Void>

    @FormUrlEncoded
    @POST("game-experiences-access")
    fun getGamesAccess(@Field("siteKey") siteKey: String,
                       @Field("shopperId") shopperId: String,
                       @Field("count") count: Int,
                       @Body filters: GameFilter): Observable<GameResponse>

    @GET("{auth_key}/{customerId}/banners/{format}/{page_type}/banner/")
    fun getBannerExperience(@Path("auth_key") auth_key: String,
                            @Path("customerId") customerId: String,
                            @Path("page_type") page_type: String,
                            @Path("format") format: String,
                            @Query("pageId") pageId: String?,
                            @Query("store") store: String,
                            @Query("store_type") store_type: String
    ): Observable<BannerResponse>

}