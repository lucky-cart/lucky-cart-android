package com.luckycart.views

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.luckycart.model.BannerDetails
import com.luckycart.model.Game
import com.luckycart.sdk.R
import com.luckycart.utils.INTENT_WEBVIEW_URL
import com.luckycart.webviews.WebViewActivity
import kotlinx.android.synthetic.main.item_banner.view.*

class BannerView : ConstraintLayout {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context,
                attrs: AttributeSet,
                defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.item_banner, this)
    }

    fun setBannerParams(bannerDetails: BannerDetails?, listner: OnClickListener?) {
        Log.e("test", "bannerDetails?.image_url =" + bannerDetails?.image_url)
        Glide.with(context).load(bannerDetails?.image_url).into(imgBanner)
        if (bannerDetails?.action?.type.isNullOrEmpty()) {
            imgBanner.setOnClickListener {
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra(INTENT_WEBVIEW_URL, bannerDetails?.redirect_url)
                context.startActivity(intent)
            }
        } else {
            imgBanner.setOnClickListener(listner)
        }
    }

    fun setGame(game: Game?) {
        Glide.with(context).load(game?.mobileGameImage).into(imgBanner)
        imgBanner.setOnClickListener{
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(INTENT_WEBVIEW_URL, game?.mobileGameUrl)
            context.startActivity(intent)
        }
    }

    fun getImageBanner(): ImageView {
        return imgBanner
    }
}