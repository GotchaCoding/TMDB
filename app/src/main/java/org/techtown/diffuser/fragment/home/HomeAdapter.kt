package org.techtown.diffuser.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import org.techtown.diffuser.R
import org.techtown.diffuser.clickInterface.MoreViewClick
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.model.TitleModel
import org.techtown.diffuser.model.WrappingModel

class HomeAdapter(
    private val itemClickListener: (View, Int, Movie?) -> Unit,
    private val moreViewClick: MoreViewClick
) : ListAdapter<ItemModel, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_TITLE -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_titlepopualr, parent, false)
                return TitleViewHolder(itemView, moreViewClick)
            }
            VIEW_TYPE_POPULAR_MOVIE -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.viewholder_popularmovies, parent, false)
                return HorizontalPopularMoviesViewHolder(itemView, itemClickListener)
            }
            VIEW_TYPE_NOW_MOVIE -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.viewholder_popularmovies, parent, false)
                return NowMovieViewHolder(itemView, itemClickListener)
            }
            VIEW_TYPE_UPCOMMING -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.viewholder_popularmovies, parent, false)
                return HorizontalPopularMoviesViewHolder(itemView, itemClickListener)
            }
            else -> {
                throw Exception()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val itemModel = currentList[position]
        when (itemModel.viewType) {
            VIEW_TYPE_TITLE -> {
                if (itemModel is TitleModel) {
                    (holder as TitleViewHolder).setItem(itemModel)
                }
            }
            VIEW_TYPE_POPULAR_MOVIE -> {
                if (holder is HorizontalPopularMoviesViewHolder && itemModel is WrappingModel) {  //is 는 자료 반환형 체크
                    holder.setItem(itemModel)
                }
            }
            VIEW_TYPE_NOW_MOVIE -> {
                if (holder is NowMovieViewHolder && itemModel is WrappingModel) {
                    holder.setItem(itemModel)
                }
            }
            VIEW_TYPE_UPCOMMING -> {
                if (holder is HorizontalPopularMoviesViewHolder && itemModel is WrappingModel) {
                    holder.setItem(itemModel)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }

    class HorizontalPopularMoviesViewHolder(
        itemView: View,
        private val ItemClickListener: (View, Int, Movie?) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private var rvMain: RecyclerView = itemView.findViewById(R.id.rvMain)
        private var vLoading: LottieAnimationView =  itemView.findViewById(R.id.vLoading)
        private var viewFailure: TextView = itemView.findViewById(R.id.onFailure)

        var adapter = HorizontalPopularMoviesRecyclerAdapter(ItemClickListener)

        init {
            vLoading.setAnimation(R.raw.loading)
            vLoading.repeatCount = 10
            vLoading.playAnimation()
            rvMain.adapter = adapter
            rvMain.layoutManager = LinearLayoutManager(
                itemView.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        fun setItem(item: WrappingModel) {
            if (item.isFailure) {
                viewFailure.isVisible = true
                vLoading.isVisible = item.isLoading
            } else {
                viewFailure.isVisible = false
                vLoading.isVisible = item.isLoading

                if (item.model != null) {
                    adapter.setMovies(item.model.movies)
                }
            }

            viewFailure.setOnClickListener {
                ItemClickListener(it, item.viewType, null)
            }
        }

    }

    class TitleViewHolder(
        itemView: View,
        private val moreViewClick: MoreViewClick
    ) : RecyclerView.ViewHolder(itemView) {

        private var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private var tvMore: TextView = itemView.findViewById(R.id.tvMoreview)

        fun setItem(titleModel: TitleModel) {
            tvTitle.text = titleModel.title
            tvMore.setOnClickListener { _ ->
                titleModel.theMore?.let {
                    moreViewClick.onClick(it)
                }
            }
        }
    }

    class NowMovieViewHolder(
        itemView: View,
        private val itemClickListener: (View, Int, Movie?) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private var rvMain: RecyclerView = itemView.findViewById(R.id.rvMain)
        private var vLoading: LottieAnimationView = itemView.findViewById(R.id.vLoading)
        private var viewFailure: TextView = itemView.findViewById(R.id.onFailure)

        var adapter = HorizontalNowPlayingAdapter(itemClickListener)

        init {
            vLoading.setAnimation(R.raw.loading)
            vLoading.repeatCount = 10
            vLoading.playAnimation()
            rvMain.adapter = adapter
            rvMain.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        }

        fun setItem(item: WrappingModel) {
            if (item.isFailure) {
                viewFailure.isVisible = true
                vLoading.isVisible = item.isLoading
            } else {
                viewFailure.isVisible = false
                vLoading.isVisible = item.isLoading

                if (item.model != null) {
                    adapter.submitList(item.model.movies)
                }
            }

            viewFailure.setOnClickListener {
                itemClickListener(it, item.viewType, null)
            }
        }
    }

    companion object {
        const val VIEW_TYPE_TITLE = 0
        const val VIEW_TYPE_POPULAR_MOVIE = 1
        const val VIEW_TYPE_NOW_MOVIE = 2
        const val VIEW_TYPE_UPCOMMING = 3
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