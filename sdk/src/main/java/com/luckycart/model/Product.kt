package com.luckycart.model

data class Product(
    val productId: String,
    val unitAtiAmount: Double,
    val unitTfAmount: Double,
    val quantity: String,
    val category: String?,
    val brand: String,
)