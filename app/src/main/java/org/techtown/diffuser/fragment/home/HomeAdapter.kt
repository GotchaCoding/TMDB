package org.techtown.diffuser.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import org.techtown.diffuser.R
import org.techtown.diffuser.listener.PopularClickListener
import org.techtown.diffuser.model.HorizontalPopularModel
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.model.Title

class HomeAdapter(private val ItemClickListener: PopularClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<ItemModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_TITLE -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_titlepopualr, parent, false)
                return TitleViewHolder(itemView)
            }
            VIEW_TYPE_POPULAR_MOVIE -> {
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
        val itemModel = items[position]
        when (itemModel.viewType) {
            VIEW_TYPE_TITLE -> {
                if (itemModel is Title) {
                    (holder as TitleViewHolder).setItem(itemModel)

                }
            }
            VIEW_TYPE_POPULAR_MOVIE -> {
                if (itemModel is HorizontalPopularModel) {
                    (holder as HorizontalPopularMoviesViewHolder).setItem(itemModel)
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

    fun addItems(items: List<ItemModel>) {
        val positionStart: Int = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(positionStart, items.size)
    }

    class HorizontalPopularMoviesViewHolder(
        itemView: View,
        private val popularItemClick: PopularClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        var rvMain: RecyclerView
        var adapter = HorizontalPopularMoviesRecyclerAdapter(popularItemClick)
        init {
            rvMain = itemView.findViewById(R.id.rvMain)
            rvMain.adapter = adapter
            rvMain.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        }

        fun setItem(item: HorizontalPopularModel) {
            adapter.setMoives(item.movies)
        }

    }

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {  //뷰홀더 for title

        var tvTitle: TextView

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
        }

        fun setItem(item: Title) {
            tvTitle.text = item.titleM
        }

    }

    companion object {
        const val VIEW_TYPE_TITLE = 0
        const val VIEW_TYPE_POPULAR_MOVIE = 1
    }
}