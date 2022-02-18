package com.luckycart.sdk

import android.content.Context
import android.widget.Toast
import com.google.gson.JsonObject
import com.luckycart.local.Prefs
import com.luckycart.model.*
import com.luckycart.retrofit.BannerDataManager
import com.luckycart.retrofit.cart.TransactionDataManager
import com.luckycart.utils.AUTH_V
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
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
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

    fun getBannerDetails(pageType: String,format:String, pageID: String) {
        val formatPage: String = if (pageID.isEmpty())
            format
        else format+"_"+pageID
        val customer = Prefs(mContext).customer
        val key = Prefs(mContext).key
        key?.let { auth_key ->
            customer?.let { customer ->
                bannerDataManager.getBannerDetails(auth_key, customer, pageType,formatPage)
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
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
            transactionDataManager.sendCart(deepMerge(cart, cartTransaction))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TransactionResponse>() {
                    override fun onNext(response: TransactionResponse) {
                        luckyCartListener?.sendCart(response)
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
                transactionDataManager.getGames(key, cartID, it).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<GameResponse>() {
                        override fun onNext(listGame: GameResponse) {
                            luckyCartListener?.getGame(listGame)
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