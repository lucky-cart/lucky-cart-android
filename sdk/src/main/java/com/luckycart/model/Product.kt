package com.luckycart.model

data class Product(
    val productId: String,
    val unitAtiAmount: Float,
    val finalAtiAmount: Float,
    val quantity: String,
    val finalTfAmount: Float? = null,
    val unitTfAmount: Float? = null,
    val discountAtiAmount: Float? = null,
    val discountTfAmount: Float? = null,
    val category: String? = null,
    val brand: String? = null,
    val ean: String? = null,
)