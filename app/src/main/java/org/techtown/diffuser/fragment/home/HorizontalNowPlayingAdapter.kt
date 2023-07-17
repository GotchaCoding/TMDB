package org.techtown.diffuser.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.R
import org.techtown.diffuser.databinding.ItemNowplayingBinding
import org.techtown.diffuser.model.Movie

class HorizontalNowPlayingAdapter(private val ItemClickListener: (View, Int, Movie?, TheMore?) -> Unit) :
    ListAdapter<Movie, NowMovieViewHolder>(diffUtil_movie) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemNowplayingBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_nowplaying, parent, false)
        return NowMovieViewHolder(binding, ItemClickListener)
    }

    override fun onBindViewHolder(holder: NowMovieViewHolder, position: Int) {
        val movie = currentList[position]
        holder.setItem(movie)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}

class NowMovieViewHolder(
    val binding: ItemNowplayingBinding,
    private val ItemClickListener: (View, Int, Movie?, TheMore?) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun setItem(item: Movie) {
        with(binding){
            movieItem = item
            executePendingBindings()
        }
        itemView.setOnClickListener {
            ItemClickListener(it, item.viewType, item, TheMore.THEMORE_NOW)
        }
    }
}

val diffUtil_movie = object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id   // === hash 비교.       == 안에잇는 내용 컨텐츠 비교
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.equals(newItem)
    }
}