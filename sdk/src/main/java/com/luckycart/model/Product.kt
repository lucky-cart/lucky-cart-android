package com.luckycart.model

data class Product(
    val brand: String,
    val category: String?,
    val productId: String,
    val quantity: String,
    val unitAtiAmount: Double,
    val unitTfAmount: Double
)