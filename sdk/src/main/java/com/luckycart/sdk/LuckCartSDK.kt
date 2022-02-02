package com.luckycart.sdk


import android.content.Context
import android.widget.Toast
import com.luckycart.local.Prefs
import com.luckycart.model.BannerDetails
import com.luckycart.model.Banners
import com.luckycart.model.LCAuthorization
import com.luckycart.retrofit.BannerDataManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class LuckCartSDK(context: Context) {
    var luckyCartListener: LuckyCartListenerCallback? = null
    var mContext = context
    private var bannerDataManager: BannerDataManager = BannerDataManager()

    fun init(authorization: LCAuthorization, customer: String?) {
        if (customer != null) {
            Prefs(mContext).customer = customer
        } else Prefs(mContext).customer = "unknown"
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

}