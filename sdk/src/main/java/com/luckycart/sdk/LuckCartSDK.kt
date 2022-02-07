package com.luckycart.sdk


import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.JsonObject
import com.luckycart.local.Prefs
import com.luckycart.model.*
import com.luckycart.retrofit.BannerDataManager
import com.luckycart.retrofit.card.TransactionDataManager
import com.luckycart.utils.HmacSignature
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.util.*

class LuckCartSDK(context: Context) {
    var luckyCartListener: LuckyCartListenerCallback? = null
    var mContext = context
    private var bannerDataManager: BannerDataManager = BannerDataManager()
    private var transactionDataManager: TransactionDataManager = TransactionDataManager()

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

    fun listAvailableBanners() {
        val customer = Prefs(mContext).customer
        val key = Prefs(mContext).key
        key?.let { auth_key ->
            customer?.let { customer ->
                bannerDataManager.listAvailableBanners(auth_key, customer)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<Banners>() {
                        override fun onNext(banners: Banners) {
                            luckyCartListener?.listAvailableBanners(banners)
                            Prefs(mContext).banners = banners
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

    fun getBannerDetails(pageType: String, pageID: String) {
        val customer = Prefs(mContext).customer
        val key = Prefs(mContext).key
        key?.let { auth_key ->
            customer?.let { customer ->
                bannerDataManager.getBannerDetails(auth_key, customer, pageType, pageID)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<BannerDetails>() {
                        override fun onNext(bannerDetails: BannerDetails) {
                            luckyCartListener?.getBannerDetails(bannerDetails)
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

    fun sendCard(cardId: String, ttc: Float) {
        val key = Prefs(mContext).key
        val customerId = Prefs(mContext).customer
        val timesTamp = (Date().time / 1000).toString()
        val sign = HmacSignature().generateSignature(timesTamp)
        val authV = "2.0"
        var cardTransaction = Card(key, timesTamp, sign, authV, cardId, customerId, ttc)
        transactionDataManager.sendCard(cardTransaction)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<TransactionResponse>() {
                override fun onNext(response: TransactionResponse) {
                    luckyCartListener?.sendCard(response)
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(mContext, "Error: " + e.message, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onComplete() {
                }


            })

    }

}