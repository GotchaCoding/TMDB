package org.techtown.diffuser.fragment.home

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

}