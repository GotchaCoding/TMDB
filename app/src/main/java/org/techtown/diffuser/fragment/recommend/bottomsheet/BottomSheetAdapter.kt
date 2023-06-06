package org.techtown.diffuser.fragment.recommend.bottomsheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.Movie

class BottomSheetAdapter(
    itemClickListener: (View, Int, Movie?, TheMore?) -> Unit
) : BaseAdapter(itemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.VIEW_TYPE_BOTTOMSHEET -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_bottomsheet_recycler, parent, false)
                BottomSheetViewHolder(itemView, itemClickListener)
            }
            Constants.VIEW_TYPE_BOTTOM_BIGPIC -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_bottomsheet_big_recycler, parent, false)
                BottomSheetBigViewHolder(itemView, itemClickListener)
            }
            else -> {
                super.onCreateViewHolder(parent, viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList[position]
        if (holder is BottomSheetViewHolder) {
            holder.setItem(item as Movie)
        }
        if (holder is BottomSheetBigViewHolder) {
            holder.setItem(item as Movie)
        }
    }
}


class BottomSheetViewHolder(
    itemView: View, private val itemClickListener: (View, Int, Movie?, TheMore?) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    var image: ImageView = itemView.findViewById(R.id.imgBottomsheet)

    fun setItem(item: Movie) {
        Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.imagePoster)
            .into(image)
        image.clipToOutline = true // 이미지를 배경에 맞게 짜름

        itemView.setOnClickListener {
            itemClickListener(it, item.viewType, item, null)
        }
    }
}

class BottomSheetBigViewHolder(
    itemView: View, private val itemClickListener: (View, Int, Movie?, TheMore?) -> Unit
) :RecyclerView.ViewHolder(itemView) {
    var image: ImageView = itemView.findViewById(R.id.imgBottomsheetBig)

    fun setItem(item: Movie) {
        Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.imageDrop)
            .into(image)
        image.clipToOutline = true // 이미지를 배경에 맞게 짜름

        itemView.setOnClickListener {
            itemClickListener(it, item.viewType, item, null)
        }
    }
}