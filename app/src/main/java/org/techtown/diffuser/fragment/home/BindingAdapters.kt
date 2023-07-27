package org.techtown.diffuser.fragment.home

import android.view.View
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

}