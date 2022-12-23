package com.luckycart.retrofit

import com.luckycart.model.BannerResponse
import com.luckycart.model.Event
import com.luckycart.model.GameExperience
import com.luckycart.model.GameFilter
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("/v1/event")
    fun sendShopperEvent(@Body event: Event): Observable<Response<Void>>

    @POST("game-experiences-access")
    fun getGamesAccess(@Query("siteKey") siteKey: String,
                       @Query("shopperId") shopperId: String,
                       @Query("count") count: Int,
                       @Body filters: GameFilter): Observable<List<GameExperience>>

    @GET("{auth_key}/{customerId}/{Type}/{platform}/{page_type}/{format}/")
    fun getBannerExperience(@Path("auth_key") auth_key: String,
                            @Path("customerId") customerId: String,
                            @Path("Type") Type: String?,
                            @Path("platform") platform: String?,
                            @Path("page_type") page_type: String,
                            @Path("format") format: String,
                            @Query("pageId") pageId: String?,
                            @Query("store") store: String?,
                            @Query("store_type") store_type: String?
    ): Observable<BannerResponse>

}