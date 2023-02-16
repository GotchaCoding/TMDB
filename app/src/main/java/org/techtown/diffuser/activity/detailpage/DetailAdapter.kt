package org.techtown.diffuser.activity.detailpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import org.techtown.diffuser.R
import org.techtown.diffuser.fragment.home.HomeAdapter
import org.techtown.diffuser.model.*

class DetailAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<ItemModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_DETAIL_BACKGROND -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_detail_image, parent, false)
                return BackImageViewHolder(itemView)
            }
            VIEW_TYPE_DETAIL_CASTING -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.viewholder_cast, parent, false)
                return CastViewHolder(itemView)
            }
            VIEW_TYPE_DETAIL_TITLE -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_titlepopualr, parent, false)
                return TitleViewHolder(itemView)
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
            VIEW_TYPE_DETAIL_CASTING -> {
                if (itemModel is WrappingDetailModel) {
                    (holder as CastViewHolder).setItem(itemModel)
                }
            }
            VIEW_TYPE_DETAIL_TITLE -> {
                if(itemModel is Title){
                    (holder as TitleViewHolder).setItem(itemModel)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    fun addItem(items: List<ItemModel>) {
        val positionStart: Int = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(positionStart, items.size)
    }

    fun updateTopModel(item: DetailTopModel) {
        items[0] = item
        notifyItemChanged(0)
    }

    fun updateCastWrappingModel(item : WrappingDetailModel) {
        items[2] = item
        notifyItemChanged(2)
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
            if(item.title != null){
                Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.backDropUrl)
                    .into(imgBackgrond)
                title.text = item.title
                overview.text = item.overview
                Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.postUrl)
                    .into(imgPoster)
            }
        }
    }

    class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var rvCast : RecyclerView
        var vLoading : LottieAnimationView
        var adapter = CastAdapter()

        init{
            rvCast = itemView.findViewById(R.id.rvCast)
            vLoading = itemView.findViewById(R.id.vLoading)
            vLoading.setAnimation("loading.json")
            vLoading.repeatCount = 10
            vLoading.playAnimation()
            rvCast.adapter = adapter
            rvCast.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        }

        fun setItem(item : WrappingDetailModel) {
            vLoading.isVisible = item.isLoading

            if(item.castModel != null) {
                adapter.setCast(item.castModel.castlist)
            }
        }
    }

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvTitle: TextView

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
        }

        fun setItem(item: Title) {
            tvTitle.text = item.titleM
        }
    }

    companion object {
        const val VIEW_TYPE_DETAIL_BACKGROND = 0
        const val VIEW_TYPE_DETAIL_CASTING = 1
        const val VIEW_TYPE_DETAIL_TITLE = 2
    }
}

