package com.luckycart.retrofit.card

import com.luckycart.utils.BASE_URL_CARD
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class TransactionApiManager {

    val transactionService: TransactionService

    companion object {

        private var transactionApiManager: TransactionApiManager? = null
        val instance: TransactionApiManager
            get() {
                if (transactionApiManager == null) {
                    transactionApiManager = TransactionApiManager()
                }
                return transactionApiManager as TransactionApiManager
            }
    }

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES) // write timeout
            .readTimeout(1, TimeUnit.MINUTES).addInterceptor(interceptor)
            .addNetworkInterceptor(BasicHeaderInterceptor()).build()
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL_CARD)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(okHttpClient).build()

        transactionService = retrofit.create(TransactionService::class.java)
    }
}

