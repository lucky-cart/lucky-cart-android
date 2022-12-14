package com.luckycart.sdk

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.google.gson.JsonObject
import com.luckycart.local.Prefs
import com.luckycart.model.*
import com.luckycart.retrofit.DataManager
import com.luckycart.utils.AUTH_V
import com.luckycart.utils.HmacSignature
import com.luckycart.utils.observeOnMain
import com.luckycart.utils.retryPolling
import com.luckycart.utils.subscribeIO
import io.reactivex.observers.DisposableObserver
import org.json.JSONObject
import java.util.Date
import java.util.concurrent.TimeUnit


class LuckCartSDK(context: Context) {

    private companion object {
        const val RETRY_AFTER = 500L
        const val MAX_ATTEMPTS = 5
    }

    var luckyCartListener: LuckyCartListenerCallback? = null
    var mContext = context
    private var dataManager: DataManager = DataManager()

    fun init(authorization: LCAuthorization, config: JSONObject?) {
        if (config == null) {
            Prefs(mContext).customer = "unknown"
        }
        Prefs(mContext).key = authorization.key
    }

    fun setUser(customer: String?) {
        if (customer != null) {
            Prefs(mContext).customer = customer
        } else Prefs(mContext).customer = "unknown"
    }

    fun setActionListener(callBack: LuckyCartListenerCallback?) {
        luckyCartListener = callBack
    }

    @Deprecated("Use getBannerExperience instead")
    fun listAvailableBanners() {
        val customer = Prefs(mContext).customer
        val key = Prefs(mContext).key
        key?.let { auth_key ->
            customer?.let { customer ->
                dataManager.listAvailableBanners(auth_key, customer)
                    .subscribeIO()
                    .observeOnMain()
                    .subscribeWith(object : DisposableObserver<Banners>() {
                        override fun onNext(banners: Banners) {
                            Prefs(mContext).banners = banners
                            luckyCartListener?.onRecieveListAvailableBanners(banners)
                        }

                        override fun onError(e: Throwable) {
                            luckyCartListener?.onError(e.message)
                        }

                        override fun onComplete() {}

                    })
            }
        }
    }

    fun getBannerDetails(pageType: String,format:String, pageID: String) {
        val formatPage: String = if (pageID.isEmpty())
            format
        else format+"_"+pageID
        val customer = Prefs(mContext).customer
        val key = Prefs(mContext).key
        key?.let { auth_key ->
            customer?.let { customer ->
                dataManager.getBannerDetails(auth_key, customer, pageType,formatPage)
                    .subscribeIO()
                    .observeOnMain()
                    .subscribeWith(object : DisposableObserver<BannerDetails>() {
                        override fun onNext(bannerDetails: BannerDetails) {
                            luckyCartListener?.onRecieveBannerDetails(bannerDetails)
                        }

                        override fun onError(e: Throwable) {
                            Toast.makeText(mContext, "Error: " + e.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onComplete() {}


                    })
            }
        }
    }

    @Deprecated("Use sendShopperEvent instead with Event Object")
    fun sendCart(cart: JsonObject) {
        val customerId = Prefs(mContext).customer
        val timesTamp = (Date().time / 1000).toString()
        val sign = HmacSignature().generateSignature(timesTamp)
        val cartTransaction = JsonObject()
        Prefs(mContext).key?.let { key ->
            cartTransaction.addProperty("auth_key", key)
            cartTransaction.addProperty("auth_ts", timesTamp)
            cartTransaction.addProperty("auth_sign", sign)
            cartTransaction.addProperty("auth_v", AUTH_V)
            cartTransaction.addProperty("customerId", customerId)
            dataManager.sendCart(deepMerge(cart, cartTransaction))
                .subscribeIO()
                .observeOnMain()
                .subscribeWith(object : DisposableObserver<TransactionResponse>() {
                    override fun onNext(response: TransactionResponse) {
                        luckyCartListener?.onRecieveSendCartTransactionResponse(response)
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(mContext, "Error: " + e.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onComplete() {
                    }
                })
        }
    }

    fun getGame(cartID: String) {
        val customerId = Prefs(mContext).customer
        Prefs(mContext).key?.let { key ->
            customerId?.let {
                dataManager.getGames(key, cartID, it)
                    .subscribeIO()
                    .observeOnMain()
                    .subscribeWith(object : DisposableObserver<GameResponse>() {
                        override fun onNext(listGame: GameResponse) {
                            luckyCartListener?.onRecieveListGames(listGame)
                        }

                        override fun onError(e: Throwable) {
                            luckyCartListener?.onError(e.message)
                        }

                        override fun onComplete() {
                        }

                    })
            }
        }
    }

    @SuppressLint("CheckResult")
    fun sendShopperEvent(event: Event) {
        val customerId = Prefs(mContext).customer
        dataManager.sendShopperEvent(event)
            .subscribeIO()
            .observeOnMain()
            .subscribeWith(object : DisposableObserver<Void>() {

                override fun onNext(void: Void) {
                    luckyCartListener?.onPostEvent("Success")
                }

                override fun onError(e: Throwable) {
                    luckyCartListener?.onError(e.message)
                }

                override fun onComplete() {
                }

            })
    }

    @SuppressLint("CheckResult")
    fun getBannerExperience(page_type: String,
                            format: String,
                            pageId: String?,
                            store: String?,
                            store_type: String?){

        val customer = Prefs(mContext).customer
        val auth_key = Prefs(mContext).key
        if(customer != null && auth_key != null){
            var attempt =0
            dataManager.getBannerExperience(auth_key, customer,
                    page_type,
                format,pageId, store, store_type)
                .subscribeIO()
                .observeOnMain()
                .doOnNext {
                   if(it.bannerList?.isEmpty() == true && attempt < MAX_ATTEMPTS){
                       attempt++
                       throw Exception("Empty Data")
                   }
                }
                .retryPolling(
                    predicate = { response ->
                        response is Exception
                    },
                    delayBeforeRetry = RETRY_AFTER,
                    maxRetry = MAX_ATTEMPTS,
                    timeUnit = TimeUnit.MILLISECONDS
                )
                .subscribeWith(object : DisposableObserver<BannerResponse>() {
                    override fun onNext(bannerResponse: BannerResponse) {
                        luckyCartListener?.onRecieveListAvailableBanners(bannerResponse)
                    }

                    override fun onError(e: Throwable) {
                        luckyCartListener?.onError(e.message)
                    }

                    override fun onComplete() {

                    }
                })

        }
    }

    @SuppressLint("CheckResult")
    fun getGamesAccess(siteKey: String,
                       count: Int?,
                       filters: GameFilter){
        val shopperId = Prefs(mContext).customer
        val countNotNull = count ?: 1
        if(shopperId != null ){
            var attempt =0
            dataManager.getGamesAccess(siteKey, shopperId, countNotNull, filters)
                .subscribeIO()
                .observeOnMain()
                .doOnNext {
                    if(it.games?.isEmpty() == true && attempt < MAX_ATTEMPTS){
                        attempt++
                        throw Exception("Empty Data")
                    }
                }
                .retryPolling(
                    predicate = { response ->
                        response is Exception
                    },
                    delayBeforeRetry = RETRY_AFTER,
                    maxRetry = MAX_ATTEMPTS,
                    timeUnit = TimeUnit.MILLISECONDS
                )
                .subscribeWith(object : DisposableObserver<GameResponse>() {

                    override fun onNext(gameResponse: GameResponse) {
                        luckyCartListener?.onRecieveListGames(gameResponse)
                    }

                    override fun onError(e: Throwable) {
                        luckyCartListener?.onError(e.message)
                    }

                    override fun onComplete() {
                    }

                })
        }

    }

    private fun deepMerge(source: JsonObject, target: JsonObject): JsonObject {
        for ((key, value) in source.entrySet()) {
            if (!target.has(key)) {
                if (!value.isJsonNull)
                    target.add(key, value)
            } else {
                if (!value.isJsonNull) {
                    if (value.isJsonObject) {
                        deepMerge(value.asJsonObject, target[key].asJsonObject)
                    } else {
                        target.add(key, value)
                    }
                } else {
                    target.remove(key)
                }
            }
        }
        return target
    }
}