package com.luckycart.model

data class Card (
    var auth_key: String?,
    var auth_ts: String?,
    var auth_sign: String?,
    var auth_v: String?,
    var cartId: String?,
    var customerId: String?,
    var ttc: Float?
)