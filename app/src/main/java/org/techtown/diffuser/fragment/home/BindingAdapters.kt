package org.techtown.diffuser.fragment.home

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("nowImageUrl")
    fun bindNowImageUrl(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context).load("https://image.tmdb.org/t/p/w500$imageUrl")
            .into(imageView)
        imageView.clipToOutline = true
    }

    @JvmStatic
    @BindingAdapter("posterImageUrl")
    fun bindPosterImageUrl(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context).load("https://image.tmdb.org/t/p/w500$imageUrl")
            .into(imageView)
        imageView.clipToOutline = true
    }

}