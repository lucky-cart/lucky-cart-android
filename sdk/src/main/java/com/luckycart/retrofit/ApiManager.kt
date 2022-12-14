package com.luckycart.retrofit

import com.luckycart.utils.BASE_URL_BANNER
import com.luckycart.utils.BASE_URL_CART
import com.luckycart.utils.BASE_URL_DISPLAYER
import com.luckycart.utils.BASE_URL_GAME
import com.luckycart.utils.BASE_URL_SHOPPER
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiManager {

    private val client by lazy {
        OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(loggerInterceptor())
            .build()
    }
    private fun loggerInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    private fun retrofitInstance(baseURl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseURl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()


    val bannerApi: ApiService by lazy {
        retrofitInstance(BASE_URL_BANNER).create(ApiService::class.java)
    }

    val cartApi: ApiService by lazy {
        retrofitInstance(BASE_URL_CART).create(ApiService::class.java)
    }

    val shopperApi: ApiService by lazy {
        retrofitInstance(BASE_URL_SHOPPER).create(ApiService::class.java)
    }
    val gamesApi: ApiService by lazy {
        retrofitInstance(BASE_URL_GAME).create(ApiService::class.java)
    }
    val displayerApi: ApiService by lazy {
        retrofitInstance(BASE_URL_DISPLAYER).create(ApiService::class.java)
    }
}