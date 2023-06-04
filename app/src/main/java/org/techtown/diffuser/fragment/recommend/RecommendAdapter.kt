package org.techtown.diffuser.fragment.recommend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.activity.detailpage.TitleViewHolder
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.model.TitleModel

class RecommendAdapter(
    itemClickListener: (View, Int, Movie?, TheMore?) -> Unit
) : BaseAdapter(itemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.VIEW_TYPE_RECOMMEND_TITLE -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_titlepopualr, parent, false)
                TitleViewHolder(itemView)
            }
            Constants.VIEW_TYPE_RECOMMEND_ITEM -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_recommend, parent, false)
                RecommendViewHolder(itemView, itemClickListener)
            }
            else -> {
                super.onCreateViewHolder(parent, viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList[position]
        if (holder is RecommendViewHolder) {
            holder.setItem(item as Movie)
        } else if (holder is TitleViewHolder) {
            holder.setItem(item as TitleModel)
        }
    }

    class RecommendViewHolder(
        itemView: View, private val itemClickListener: (View, Int, Movie?, TheMore?) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.imgGrid)
        var bookMark: CheckBox = itemView.findViewById(R.id.checkbox)

        fun setItem(item: Movie) {
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.imagePoster)
                .into(image)
            image.clipToOutline = true // 이미지를 배경에 맞게 짜름

            itemView.setOnClickListener {
                itemClickListener(it, item.viewType, item, null)
            }
            bookMark.isChecked = item.isCheckedMark
            bookMark.setOnClickListener {
                item.isCheckedMark = bookMark.isChecked
            }
        }
    }

}