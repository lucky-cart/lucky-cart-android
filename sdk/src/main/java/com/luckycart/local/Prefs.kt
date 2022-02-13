package com.luckycart.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.luckycart.model.Banners

class Prefs(context: Context) {

    private var gson = Gson()
    private val preferences: SharedPreferences =
        context.getSharedPreferences("PREFERENCE_SDK", Context.MODE_PRIVATE)
    var customer: String?
        get() = preferences.getString("customer", "")
        set(value) = preferences.edit().putString("customer", value).apply()
    var key: String?
        get() = preferences.getString("key", "")
        set(value) = preferences.edit().putString("key", value).apply()
    var banners: Banners
        get() = gson.fromJson(preferences.getString("listBanner", ""), Banners::class.java)
        set(value) = preferences.edit().putString("listBanner", gson.toJson(value)).apply()

}
