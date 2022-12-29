package com.luckycart.model

data class Event(
    val shopperId: String,
    val siteKey: String,
    val eventName: String? = null,
    val ipAddress: String? = null,
    val payload: EventPayload? = null
)