package com.luckycart.model

data class Event(
    val eventName: String?,
    val payload: EventPayload?,
    val shopperId: String,
    val siteKey: String
)