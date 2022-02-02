package com.luckycart.local

import android.content.Context
import android.content.SharedPreferences


class Prefs(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("PREFERENCE_SDK", Context.MODE_PRIVATE)

    var customer: String?
        get() = preferences.getString("customer", "")
        set(value) = preferences.edit().putString("customer", value).apply()

    var key: String?
        get() = preferences.getString("key", "")
        set(value) = preferences.edit().putString("key", value).apply()
}
