package com.luckycart.model

data class TransactionResponse(
    var ticket: String?,
    var mobileUrl: String?,
    var tabletUrl: String?,
    var desktopUrl: String?,
    var baseMobileUrl: String?,
    var baseTabletUrl: String?,
    var baseDesktopUrl: String?,
    var baseIntroDesktop: String?,
    var introDesktop: String?,
    var linkDesktop: String?,
    var baseIntroMobile: String?,
    var introMobile: String?,
    var linkMobile: String?,
    var baseIntroTablet: String?,
    var introTablet: String?,
    var linkTablet: String?
)