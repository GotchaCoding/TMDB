package org.techtown.diffuser.activity.detailpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import org.techtown.diffuser.R
import org.techtown.diffuser.model.*

class DetailAdapter(val ItemClickListener :  (View, Int, Movie?) -> Unit) :
    ListAdapter<ItemModel, RecyclerView.ViewHolder>(diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_DETAIL_BACKGROND -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_detail_image, parent, false)
                return BackImageViewHolder(itemView, ItemClickListener)
            }
            VIEW_TYPE_DETAIL_CASTING -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.viewholder_cast, parent, false)
                return CastViewHolder(itemView, ItemClickListener)
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
        val itemModel = currentList[position]
        when (itemModel.viewType) {
            VIEW_TYPE_DETAIL_BACKGROND -> {
                if (itemModel is WrappingDetailModel) {
                    (holder as BackImageViewHolder).setItem(itemModel)
                }
            }
            VIEW_TYPE_DETAIL_CASTING -> {
                if (itemModel is WrappingDetailModel) {
                    (holder as CastViewHolder).setItem(itemModel)
                }
            }
            VIEW_TYPE_DETAIL_TITLE -> {
                if (itemModel is TitleModel) {
                    (holder as TitleViewHolder).setItem(itemModel)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }


    class BackImageViewHolder(itemView: View, val ItemClickListener :  (View, Int, Movie?) -> Unit ) :
        RecyclerView.ViewHolder(itemView) {
        var imgBackgrond: ImageView
        var imgPoster: ImageView
        var title: TextView
        var overview: TextView
        var vLoading: LottieAnimationView
        var view_failure: TextView

        init {
            imgBackgrond = itemView.findViewById(R.id.img_background)
            imgPoster = itemView.findViewById(R.id.img_poster)
            title = itemView.findViewById(R.id.tvTitle_detail)
            overview = itemView.findViewById(R.id.tvOverview)
            vLoading = itemView.findViewById(R.id.vLoading_topmodel)
            vLoading.setAnimation(R.raw.loading)
            vLoading.repeatCount = 10
            vLoading.playAnimation()
            view_failure = itemView.findViewById(R.id.onFailure_detail)

        }

        fun setItem(item: WrappingDetailModel) {

            if (item.isLoading) { //로딩중
                vLoading.isVisible = true
                view_failure.isVisible = false
            } else if (item.detailTopModel?.isfailure == false) { //성공
                vLoading.isVisible = false
                view_failure.isVisible = false
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500" + item.detailTopModel.backDropUrl)
                    .into(imgBackgrond)
                title.text = item.detailTopModel.title
                overview.text = item.detailTopModel.overview
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500" + item.detailTopModel.postUrl)
                    .into(imgPoster)
            } else {//실패
                view_failure.isVisible = true
                vLoading.isVisible = false
                view_failure.setOnClickListener {
                    ItemClickListener(it, VIEW_TYPE_DETAIL_BACKGROND, null)
                }
            }


        }
    }

    class CastViewHolder(itemView: View, val ItemClickListener :  (View, Int, Movie?) -> Unit ) :
        RecyclerView.ViewHolder(itemView) {

        var rvCast: RecyclerView
        var vLoading: LottieAnimationView
        var adapter = CastAdapter()
        var view_failure: TextView

        init {
            rvCast = itemView.findViewById(R.id.rvCast)
            vLoading = itemView.findViewById(R.id.vLoading)
            vLoading.setAnimation(R.raw.loading)
            vLoading.repeatCount = 10
            vLoading.playAnimation()
            rvCast.adapter = adapter
            rvCast.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            view_failure = itemView.findViewById(R.id.onFailure)
        }

        fun setItem(item: WrappingDetailModel) {
            if (item.isFailure) {
                view_failure.isVisible = true
                vLoading.isVisible = item.isLoading
            } else {
                view_failure.isVisible = false
                vLoading.isVisible = item.isLoading

                if (item.castModel != null) {
                    adapter.submitList(item.castModel.castlist)

                }
            }
            view_failure.setOnClickListener {
                ItemClickListener(it, item.viewType, null)
            }
        }
    }

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvTitle: TextView
        var tvMoreview : TextView

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvMoreview = itemView.findViewById(R.id.tvMoreview)
        }

        fun setItem(item: TitleModel) {
            tvTitle.text = item.title
            tvMoreview.text = ""
        }
    }

    companion object {
        const val VIEW_TYPE_DETAIL_BACKGROND = 0
        const val VIEW_TYPE_DETAIL_CASTING = 1
        const val VIEW_TYPE_DETAIL_TITLE = 2
    }
}

val diffUtil = object : DiffUtil.ItemCallback<ItemModel>() {
    override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
        return oldItem.equals(newItem)
    }

}