package org.techtown.diffuser.activity.detailpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.diffuser.R
import org.techtown.diffuser.model.DetailTopModel
import org.techtown.diffuser.model.ItemModel

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
                if (itemModel is DetailTopModel) {
                    (holder as BackImageViewHolder).setItem(itemModel)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(items: List<ItemModel>) {
        val positionStart: Int = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(positionStart, items.size)
    }

    fun setTopModel(topModel: DetailTopModel) {
        items[0] = topModel
        notifyItemChanged(0)
    }

    class BackImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgBackgrond: ImageView
        var imgPoster: ImageView
        var title: TextView
        var overview: TextView

        init {
            imgBackgrond = itemView.findViewById(R.id.img_background)
            imgPoster = itemView.findViewById(R.id.img_poster)
            title = itemView.findViewById(R.id.tvTitle_detail)
            overview = itemView.findViewById(R.id.tvOverview)
        }

        fun setItem(item: DetailTopModel) {
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.backDropUrl)
                .into(imgBackgrond)
            title.text = item.title
            overview.text = item.overview
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.postUrl).into(imgPoster)
        }
    }

    companion object {
        const val VIEW_TYPE_DETAIL_BACKGROND = 0
        const val VIEW_TYPE_DETAIL_CASTING = 1
    }
}

