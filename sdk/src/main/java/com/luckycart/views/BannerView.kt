package com.luckycart.views

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.luckycart.model.Banner
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

    fun setBannerParams(banner: Banner, clickListener: OnClickListener?, listener: (Banner) -> Unit) {
        Glide.with(context).load(banner?.imageUrl).into(imgBanner)
        if (banner?.shopInShopRedirect != null) {
            imgBanner.setOnClickListener {
                listener.invoke(banner)
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra(INTENT_WEBVIEW_URL, banner.shopInShopRedirect)
                context.startActivity(intent)
            }
        } else {
            imgBanner.setOnClickListener {
                clickListener
                listener.invoke(banner)
            }
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