package com.luckycart.model

data class Event(
    val shopperId: String,
    val siteKey: String,
    val eventName: String?,
    val payload: EventPayload?
)