package org.techtown.diffuser.activity.detailpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.diffuser.R
import org.techtown.diffuser.model.HorizontalMoviesModel
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie

class DetailAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<ItemModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_DETAIL_BACKGROND -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_detail_image, parent, false)
                return BackImageViewHolder(itemView)
            }
            else -> {
                throw Exception()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemModel = items[position]
        when (itemModel.viewType) {
            VIEW_TYPE_DETAIL_BACKGROND -> {
                if(itemModel is HorizontalMoviesModel) {
                    (holder as BackImageViewHolder).setItem(itemModel)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")

    }

    class BackImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgBackgrond: ImageView

        init {
            imgBackgrond = itemView.findViewById(R.id.img_background)
        }

        fun setItem(item: HorizontalMoviesModel) {
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.movies)
                .into(imgBackgrond)
        }
    }

    companion object {
        const val VIEW_TYPE_DETAIL_BACKGROND = 0
        const val VIEW_TYPE_DETAIL_CASTING = 1
    }
}

