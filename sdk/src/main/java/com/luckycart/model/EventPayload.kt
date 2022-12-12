package com.luckycart.model

data class EventPayload(
    val cartId: Int,
    val currency: String,
    val deliveryAtiAmount: Double,
    val deliveryTfAmount: Double,
    val device: String,
    val finalAtiAmount: Double,
    val products: List<Product>
)