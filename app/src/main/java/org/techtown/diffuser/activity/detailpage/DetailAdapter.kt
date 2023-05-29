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
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.model.TitleModel
import org.techtown.diffuser.model.WrappingDetailModel

class DetailAdapter(itemClickListener: (View, Int, Movie?, TheMore?) -> Unit) :
    BaseAdapter(itemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_DETAIL_BACKGROND -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_detail_image, parent, false)
                return BackImageViewHolder(itemView, itemClickListener)
            }
            VIEW_TYPE_DETAIL_CASTING -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_detail_cast, parent, false)
                return CastViewHolder(itemView, itemClickListener)
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


    class BackImageViewHolder(
        itemView: View,
        val ItemClickListener: (View, Int, Movie?, TheMore?) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {
        private val imgBackgrond: ImageView = itemView.findViewById(R.id.img_background)
        private val imgPoster: ImageView = itemView.findViewById(R.id.img_poster)
        private val title: TextView = itemView.findViewById(R.id.tvTitle_detail)
        private val overview: TextView = itemView.findViewById(R.id.tvOverview)
        private val vLoading: LottieAnimationView = itemView.findViewById(R.id.vLoading_topmodel)
        private val viewFailure: TextView = itemView.findViewById(R.id.onFailure_detail)

        init {
            vLoading.setAnimation(R.raw.loading)
            vLoading.repeatCount = -1
            vLoading.playAnimation()
        }

        fun setItem(item: WrappingDetailModel) {

            if (item.isLoading) { //로딩중
                vLoading.isVisible = true
                viewFailure.isVisible = false
            } else if (item.detailTopModel?.isFailure == false) { //성공
                vLoading.isVisible = false
                viewFailure.isVisible = false
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500" + item.detailTopModel.backDropUrl)
                    .into(imgBackgrond)
                title.text = item.detailTopModel.title
                overview.text = item.detailTopModel.overview
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500" + item.detailTopModel.postUrl)
                    .into(imgPoster)
            } else {//실패
                viewFailure.isVisible = true
                vLoading.isVisible = false
                viewFailure.setOnClickListener {
                    ItemClickListener(it, VIEW_TYPE_DETAIL_BACKGROND, null, null)
                }
            }
        }
    }

    class CastViewHolder(
        itemView: View,
        val ItemClickListener: (View, Int, Movie?, TheMore?) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {

        var rvCast: RecyclerView = itemView.findViewById(R.id.rvCast)
        var vLoading: LottieAnimationView = itemView.findViewById(R.id.vLoading)
        var adapter =
            CastAdapter(itemClickListener = { _,_,_,_ -> })  //todo  추후 확인 필요. 베이스어뎁터에 itemClickListener를 넣으니 이곳도 어떤 처리가 필요함.
        var view_failure: TextView

        init {
            vLoading.setAnimation(R.raw.loading)
            vLoading.repeatCount = -1
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
                    adapter.submitList(item.castModel.castList)
                }
            }
            view_failure.setOnClickListener {
                ItemClickListener(it, item.viewType, null, null)
            }
        }
    }



    companion object {
        const val VIEW_TYPE_DETAIL_BACKGROND = 0
        const val VIEW_TYPE_DETAIL_CASTING = 1
        const val VIEW_TYPE_DETAIL_TITLE = 2
    }
}
