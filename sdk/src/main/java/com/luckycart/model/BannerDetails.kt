package com.luckycart.model

data class BannerDetails(
    var image_url: String,
    var redirect_url: String,
    var name: String,
    var campaign: String,
    var space: String,
    var action: BannerAction
)