package com.luckycart.retrofit

import com.luckycart.model.Banner

sealed class BannerExperienceState {
    class OnSuccess(val bannerList: List<Banner>) : BannerExperienceState()
    class OnError(val error: String) : BannerExperienceState()
}