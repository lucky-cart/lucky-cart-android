package com.luckycart.model

data class EventPayload(
    val cartId: Int,
    val shopperEmail: String? = null,
    val transactionDate: String? = null,
    val storeId: String? = null,
    val storeTypeId: String? = null,
    val currency: String? = null,
    val device: String? = null,
    val lang: String? = null,
    val finalAtiAmount: Float? = null,
    val finalTfAmount: Float? = null,
    val totalDiscountAtiAmount: Float? = null,
    val totalDiscountTfAmount: Float? = null,
    val deliveryAtiAmount: Float? = null,
    val deliveryTfAmount: Float? = null,
    val deliveryMode: String? = null,
    val deliveryDate: String? = null,
    val paymentType: String? = null,
    val promoCode: String? = null,
    val promoCodeAtiAmount: Float? = null,
    val promoCodeLabel: String? = null,
    val loyaltyCard: String? = null,
    val hasLoyaltyCard: Boolean? = null,
    val isNewShopper: Boolean? = null,

    val page: String? = null,
    val pageType: String? = null,
    val pageId: String? = null,
    val products: List<Product>? = null
)