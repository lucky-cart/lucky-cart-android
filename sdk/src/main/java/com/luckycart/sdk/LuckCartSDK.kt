package com.luckycart.sdk


import android.content.Context
import com.luckycart.local.Prefs
import com.luckycart.model.LCAuthorization

class LuckCartSDK(context: Context) : LuckyCart {
    var mContext = context

    fun init(authorization: LCAuthorization, customer: String?) {
        if (customer != null) {
            Prefs(mContext).customer = customer
        } else Prefs(mContext).customer = "unknown"
        Prefs(mContext).key = authorization.key
    }

    override fun setUser(customer: String?) {
        if (customer != null) {
            Prefs(mContext).customer = customer
        } else Prefs(mContext).customer = "unknown"
    }

}