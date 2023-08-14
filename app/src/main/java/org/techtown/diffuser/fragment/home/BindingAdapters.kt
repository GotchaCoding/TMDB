package org.techtown.diffuser.fragment.home

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("dropImageUrl")
    fun bindNowImageUrl(view: ImageView, imageUrl: String) {
        Glide.with(view.context).load("https://image.tmdb.org/t/p/w500$imageUrl")
            .into(view)
        view.clipToOutline = true
    }

    @JvmStatic
    @BindingAdapter("posterImageUrl")
    fun bindPosterImageUrl(view: ImageView, imageUrl: String) {
        Glide.with(view.context).load("https://image.tmdb.org/t/p/w500$imageUrl")
            .into(view)
        view.clipToOutline = true
    }

    @JvmStatic
    @BindingAdapter("castImageUrl")
    fun bindCastImageUrl(view: ImageView, imageUrl: String) {
        Glide.with(view.context).load("https://image.tmdb.org/t/p/w500$imageUrl")
            .into(view)
        view.clipToOutline = true
    }

    @JvmStatic
    @BindingAdapter("visibility")
    fun bindVisibility(view: View, isVisible: Boolean) {
        if (isVisible) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("videoUrl")
    fun bindVideoUrl(view: WebView, videoUrl: String){  //todo 데이터바인딩
        view.loadData(  "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${videoUrl}\"</iframe>", "text/html", "utf-8")
        view.settings.javaScriptEnabled = true
        view.webChromeClient = WebChromeClient()
    }

}