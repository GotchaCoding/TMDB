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
import org.techtown.diffuser.activity.detailpage.DetailAdapter
import org.techtown.diffuser.listener.MovieClickListener
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Title
import org.techtown.diffuser.model.WrappingModel

class HomeAdapter(
    private val ItemClickListener: MovieClickListener,
) : ListAdapter<ItemModel, RecyclerView.ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_TITLE -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_titlepopualr, parent, false)
                return DetailAdapter.TitleViewHolder(itemView)
            }
            VIEW_TYPE_POPULAR_MOVIE -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.viewholder_popularmovies, parent, false)
                return HorizontalPopularMoviesViewHolder(itemView, ItemClickListener)
            }
            VIEW_TYPE_NOW_MOVIE -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.viewholder_popularmovies, parent, false)
                return NowMovieViewHolder(itemView, ItemClickListener)
            }
            VIEW_TYPE_UPCOMMING -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.viewholder_popularmovies, parent, false)
                return HorizontalPopularMoviesViewHolder(itemView, ItemClickListener)
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
                if (itemModel is Title) {
                    (holder as DetailAdapter.TitleViewHolder).setItem(itemModel)

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
        private val popularItemClick: MovieClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        var rvMain: RecyclerView
        var vLoading: LottieAnimationView
        var adapter = HorizontalPopularMoviesRecyclerAdapter(popularItemClick)
        var view_failure: TextView

        init {
            rvMain = itemView.findViewById(R.id.rvMain)
            vLoading = itemView.findViewById(R.id.vLoading)
            vLoading.setAnimation("loading.json")
            vLoading.repeatCount = 10
            vLoading.playAnimation()
            rvMain.adapter = adapter
            rvMain.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            view_failure = itemView.findViewById(R.id.onFailure)

        }

        fun setItem(item: WrappingModel) {
            if (item.isFailure) {
                view_failure.isVisible = true
                vLoading.isVisible = item.isLoading
            } else {
                view_failure.isVisible = false
                vLoading.isVisible = item.isLoading

                if (item.model != null) {
                    adapter.setMovies(item.model.movies)
                }
            }
            view_failure.setOnClickListener(View.OnClickListener {
                popularItemClick.onClick(it, item.viewType, null)
            })


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

    class NowMovieViewHolder(
        itemView: View,
        private val movieClickListener: MovieClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        var rvMain: RecyclerView
        var vLoading: LottieAnimationView
        var adapter = HorizontalNowPlayingAdapter(movieClickListener)
        var view_failure: TextView

        init {
            rvMain = itemView.findViewById(R.id.rvMain)
            vLoading = itemView.findViewById(R.id.vLoading)
            vLoading.setAnimation("loading.json")
            vLoading.repeatCount = 10
            vLoading.playAnimation()
            rvMain.adapter = adapter
            rvMain.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            view_failure = itemView.findViewById(R.id.onFailure)
        }

        fun setItem(item: WrappingModel) {
            if (item.isFailure) {
                view_failure.isVisible = true
                vLoading.isVisible = item.isLoading
            } else {
                view_failure.isVisible = false
                vLoading.isVisible = item.isLoading

                if (item.model != null) {
                    adapter.submitList(item.model.movies)
                }
            }
            view_failure.setOnClickListener {
                movieClickListener.onClick(it, item.viewType, null )
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