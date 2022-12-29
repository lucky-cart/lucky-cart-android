package com.luckycart.sdk

import android.annotation.SuppressLint
import android.content.Context
import com.luckycart.local.Prefs
import com.luckycart.model.*
import com.luckycart.retrofit.DataManager
import com.luckycart.utils.observeOnMain
import com.luckycart.utils.retryPolling
import com.luckycart.utils.subscribeIO
import io.reactivex.observers.DisposableObserver
import org.json.JSONObject
import retrofit2.Response
import java.util.concurrent.TimeUnit


class LuckCartSDK(context: Context) {

    private var retryAfter = 500L
    private var maxAttempts = 5

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

    fun setPollingConfig(retryAfter: Long, maxAttempts: Int) {
        this.retryAfter = retryAfter
        this.maxAttempts = maxAttempts
    }

    fun setActionListener(callBack: LuckyCartListenerCallback?) {
        luckyCartListener = callBack
    }

    @SuppressLint("CheckResult")
    fun getBannersExperience(
        page_type: String,
        format: String,
        platform: String? = "mobile",
        pageId: String? = null,
        store: String? = null,
        store_type: String? = null
    ) {

        val customer = Prefs(mContext).customer
        val auth_key = Prefs(mContext).key
        if (customer != null && auth_key != null) {
            var attempt = 0
            dataManager.getBannerExperience(
                auth_key = auth_key,
                customerId = customer,
                type = "banners",
                platform = platform,
                page_type = page_type,
                format = format,
                pageId = pageId,
                store = store,
                store_type = store_type
            )
                .subscribeIO()
                .observeOnMain()
                .doOnNext {
                    if (it.bannerList?.isEmpty() == true && attempt < maxAttempts) {
                        attempt++
                        throw Exception("Empty Data")
                    }
                }
                .retryPolling(
                    predicate = { response ->
                        response is Exception
                    },
                    delayBeforeRetry = retryAfter,
                    maxRetry = maxAttempts,
                    timeUnit = TimeUnit.MILLISECONDS
                )
                .subscribeWith(object : DisposableObserver<BannerResponse>() {
                    override fun onNext(bannerResponse: BannerResponse) {
                        bannerResponse.bannerList?.let { luckyCartListener?.onBannerListReceived(it) }
                    }

                    override fun onError(e: Throwable) {
                        luckyCartListener?.onError(e.message)
                    }

                    override fun onComplete() {

                    }
                })
        }
    }

    fun getBannerExperienceDetail(
        page_type: String,
        format: String,
        pageId: String? = null,
        platform: String? = "mobile",
        store: String? = null,
        store_type: String? = null
    ) {

        val customer = Prefs(mContext).customer
        val auth_key = Prefs(mContext).key
        customer?.let {
            auth_key?.let {
                var attempt = 0
                dataManager.getBannerExperience(
                    auth_key, customer, "banner", platform, page_type,
                    format, pageId, store, store_type
                )
                    .subscribeIO()
                    .observeOnMain()
                    .doOnNext {
                        if (it.banner == null && attempt < maxAttempts) {
                            attempt++
                            throw Exception("Empty Data")
                        }
                    }
                    .retryPolling(
                        predicate = { response ->
                            response is Exception
                        },
                        delayBeforeRetry = retryAfter,
                        maxRetry = maxAttempts,
                        timeUnit = TimeUnit.MILLISECONDS
                    )
                    .subscribeWith(object : DisposableObserver<BannerResponse>() {
                        override fun onNext(bannerResponse: BannerResponse) {
                            bannerResponse.banner?.let {
                                luckyCartListener?.onBannerDetailReceived(
                                    it
                                )
                            }
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

    fun sendShopperEvent(siteKey: String, eventName: String, payload: EventPayload?) {
        val customerId = Prefs(mContext).customer

        if (customerId != null) {
            val event = (Event(
                shopperId = customerId,
                siteKey = siteKey,
                eventName = eventName,
                payload = payload
            ))
            dataManager.run {
                sendShopperEvent(event)
                    .subscribeIO()
                    .observeOnMain()
                    .subscribeWith(object : DisposableObserver<Response<Void>>() {

                        override fun onNext(void: Response<Void>) {
                            luckyCartListener?.onPostEvent("Success")
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

    fun getGamesAccess(
        siteKey: String,
        count: Int?,
        filters: GameFilter
    ) {
        val shopperId = Prefs(mContext).customer
        val countNotNull = count ?: 1
        shopperId?.let {
            it
            var attempt = 0
            dataManager.getGamesAccess(siteKey, it, countNotNull, filters)
                .subscribeIO()
                .observeOnMain()
                .doOnNext {
                    if (it.isEmpty() && attempt < maxAttempts) {
                        attempt++
                        throw Exception("Empty Data")
                    }
                }
                .retryPolling(
                    predicate = { response ->
                        response is Exception
                    },
                    delayBeforeRetry = retryAfter,
                    maxRetry = maxAttempts,
                    timeUnit = TimeUnit.MILLISECONDS
                )
                .subscribeWith(object : DisposableObserver<List<GameExperience>>() {

                    override fun onNext(gameList: List<GameExperience>) {
                        luckyCartListener?.onGameListReceived(gameList)
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