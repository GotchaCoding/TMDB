package org.techtown.diffuser.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.R
import org.techtown.diffuser.listener.PopularClickListener
import org.techtown.diffuser.model.Movie

class HorizontalNowPlayingAdapter(private val ItemClickListener: PopularClickListener) :
    RecyclerView.Adapter<HorizontalPopularMoviesRecyclerAdapter.MovieViewHolder>() {


    var items: List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalPopularMoviesRecyclerAdapter.MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_popularmovie, parent, false)
        return HorizontalPopularMoviesRecyclerAdapter.MovieViewHolder(itemView, ItemClickListener)
    }

    override fun onBindViewHolder(holder: HorizontalPopularMoviesRecyclerAdapter.MovieViewHolder, position: Int) {
        holder.setItem(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setMovies(movies: List<Movie>) {
        items = movies
        notifyDataSetChanged()
    }


}
