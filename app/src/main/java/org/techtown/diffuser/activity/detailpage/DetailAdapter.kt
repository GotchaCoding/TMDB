package org.techtown.diffuser.activity.detailpage

import android.util.Log.v
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
import org.techtown.diffuser.fragment.home.HomeAdapter
import org.techtown.diffuser.listener.OnFailureClickListener
import org.techtown.diffuser.model.DetailTopModel
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Title
import org.techtown.diffuser.model.WrappingDetailModel

class DetailAdapter(val failureClick: OnFailureClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<ItemModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_DETAIL_BACKGROND -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_detail_image, parent, false)
                return BackImageViewHolder(itemView, failureClick)
            }
            VIEW_TYPE_DETAIL_CASTING -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.viewholder_cast, parent, false)
                return CastViewHolder(itemView, failureClick)
            }
            VIEW_TYPE_DETAIL_TITLE -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_titlepopualr, parent, false)
                return HomeAdapter.TitleViewHolder(itemView)
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
                if (itemModel is Title) {
                    (holder as HomeAdapter.TitleViewHolder).setItem(itemModel)
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

    fun updateCastWrappingModel(item: WrappingDetailModel) {
        items[2] = item
        notifyItemChanged(2)
    }


    class BackImageViewHolder(itemView: View, val failureClick: OnFailureClickListener) :
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
            vLoading.setAnimation("loading.json")
            vLoading.repeatCount = 10
            vLoading.playAnimation()
            view_failure = itemView.findViewById(R.id.onFailure_detail)

        }

        fun setItem(item: DetailTopModel) {
            if(item.isLoading){ //로딩중
                vLoading.isVisible = true
                view_failure.isVisible = false
            }else if(item.isfailure.not()) { //성공
                vLoading.isVisible = false
                view_failure.isVisible = false
                Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.backDropUrl)
                    .into(imgBackgrond)
                title.text = item.title
                overview.text = item.overview
                Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.postUrl)
                    .into(imgPoster)
            }else{//실패
                view_failure.isVisible = true
                vLoading.isVisible = false
                view_failure.setOnClickListener{
                    failureClick.onClick(it, VIEW_TYPE_DETAIL_BACKGROND)
                }
            }


        }
    }

    class CastViewHolder(itemView: View, val failureClick: OnFailureClickListener) :
        RecyclerView.ViewHolder(itemView) {

        var rvCast: RecyclerView
        var vLoading: LottieAnimationView
        var adapter = CastAdapter()
        var view_failure: TextView

        init {
            rvCast = itemView.findViewById(R.id.rvCast)
            vLoading = itemView.findViewById(R.id.vLoading)
            vLoading.setAnimation("loading.json")
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
                    adapter.setCast(item.castModel.castlist)
                }
            }
            view_failure.setOnClickListener {
                failureClick.onClick(it, item.viewType)
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

 val diffUtil = object : DiffUtil.ItemCallback<Item