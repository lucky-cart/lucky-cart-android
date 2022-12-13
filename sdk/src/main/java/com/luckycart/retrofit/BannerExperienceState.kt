package com.luckycart.retrofit

import com.luckycart.model.BannerResponse

sealed class BannerExperienceState {
    class OnSuccess(val response: BannerResponse) : BannerExperienceState()
    class OnError(val error: String) : BannerExperienceState()
}