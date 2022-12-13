package com.luckycart.model

data class EventPayload(
    val cartId: Int,
    val currency: String,
    val device: String,
    val finalAtiAmount: Double,
    val deliveryAtiAmount: Double,
    val deliveryTfAmount: Double,
    val products: List<Product>
)