package com.luckycart.sdk

import androidx.lifecycle.Observer
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.luckycart.model.Banner
import com.luckycart.model.Event
import com.luckycart.model.EventPayload
import com.luckycart.model.GameExperience
import com.luckycart.model.LCAuthorization
import com.luckycart.retrofit.DataManager
import io.mockk.spyk
import io.mockk.verify
import junit.framework.TestCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import retrofit2.Response
import kotlin.time.ExperimentalTime


@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
@LooperMode(LooperMode.Mode.PAUSED)
class LuckyCartSDKTest : TestCase() {

    val AUTH_KEY = "A2ei4iyi"
    val CUSTOMER_ID = "tom004"

    val apiResponse = object : LuckyCartListenerCallback {
        override fun onBannerListReceived(bannerList: List<Banner>) {
            println("onBannerListReceived")
        }

        override fun onBannerDetailReceived(banner: Banner) {
            println("onBannerDetailReceived")
        }

        override fun onPostEvent(success: String?) {
            println("onPostEvent")
        }

        override fun onGameListReceived(gameList: List<GameExperience>) {
            println("onGameListReceived")
        }

        override fun onError(error: String?) {
            println("onError")
        }
    }
    private val apiSpy = spyk(apiResponse)
    private var dataManager: DataManager = spyk()
    private var actionsObserver: Observer<Response<Void>> = spyk()
    val state = MutableLiveData<Response<Void>>()

    fun application(): Application {
        return ApplicationProvider.getApplicationContext() as Application
    }

    var luckyCartSDK = mockk<LuckCartSDK>()

    @Before
    fun init() {
        // initialize Lucky SDK here
        val auth = LCAuthorization(AUTH_KEY, "")
        //  luckyCartSDK = LuckCartSDK(application().applicationContext)
        every { luckyCartSDK.init(auth, null) } returns Unit
        every { luckyCartSDK.setUser(CUSTOMER_ID) } returns Unit
        every { luckyCartSDK.setPollingConfig(1000L, 3) } returns Unit
        every { luckyCartSDK.setActionListener(apiSpy) } returns Unit

        state.observeForever(actionsObserver)
    }

    @Test
    fun checkBannersExperience() {
        val res = mutableListOf<LuckyCartListenerCallback>()
        every {
            luckyCartSDK.getBannersExperience(
                page_type = "Homepage",
                format = "banner"
            )
        } returns Unit
        //verify { luckyCartListener}
        verify(exactly = 1) {
            luckyCartSDK.getBannersExperience(
                page_type = "Homepage",
                format = "banner",
                platform = "mobile",
                null,
                null,
                null
            )
        }

        luckyCartSDK.getBannersExperience(
            page_type = "Homepage",
            format = "banner",
            platform = "mobile",
            null,
            null,
            null
        )
        // assertThat(res[0]).isInstanceOf(LuckyCartListenerCallback::class)
    }

    @Test
    @ExperimentalTime
    fun checkBannerViewedEvent() {
        val eventPayload = EventPayload(
            pageType = "homepage",
            pageId = null,
            bannerType = "banner",
            bannerPosition = "homepage card",
            operationId = "621374c3c42f544d635536c5"
        )

        //every { luckyCartSDK.sendShopperEvent("site", "bannerClicked", null) } just runs
        //sting that a method is not called
        verify(exactly = 0) { luckyCartSDK.sendShopperEvent(any(), any(), any()) }
        verify(exactly = 0) { luckyCartSDK.sendShopperEvent("site", "bannerClicked", eventPayload) }
    }

    @Test
    @ExperimentalTime
    fun checkSendShopperEvent() {
        every { dataManager.sendShopperEvent(mockk<Event>()) } returns Observable.empty()
        val eventPayload = EventPayload(
            pageType = "homepage",
            pageId = null,
            bannerType = "banner",
            bannerPosition = "homepage card",
            operationId = "621374c3c42f544d635536c5"
        )
        val event = (Event(
            shopperId = CUSTOMER_ID,
            siteKey = AUTH_KEY,
            eventName = "bannerClicked",
            payload = eventPayload
        ))

        dataManager.sendShopperEvent(event)

        val actions = mutableListOf<Response<Void>>()
        verify(exactly = 1) { actionsObserver.onChanged(capture(actions)) }
        assertThat(actions[0]).isInstanceOf(LuckyCartListenerCallback::class)
    }
}