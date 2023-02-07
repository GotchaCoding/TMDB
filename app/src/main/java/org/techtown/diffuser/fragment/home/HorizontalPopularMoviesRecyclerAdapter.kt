package org.techtown.diffuser.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.diffuser.R
import org.techtown.diffuser.listener.PopularClickListener
import org.techtown.diffuser.model.Movie

class HorizontalPopularMoviesRecyclerAdapter(private val ItemClickListener: PopularClickListener) :
    RecyclerView.Adapter<HorizontalPopularMoviesRecyclerAdapter.MovieViewHolder>() {

    var items: List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_popularmovie, parent, false)
        return MovieViewHolder(itemView, ItemClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setItem(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setMoives(movies: List<Movie>) {
        items = movies
        notifyDataSetChanged()
    }

    class MovieViewHolder(itemView: View, private val popularItemClick: PopularClickListener) :
        RecyclerView.ViewHolder(itemView) {

        var title: TextView
        var rank: TextView
        var image: ImageView


        init {
            title = itemView.findViewById(R.id.tvPopularTitle)
            rank = itemView.findViewById(R.id.tvPopularRank)
            image = itemView.findViewById(R.id.imagePopular)
        }

        fun setItem(item: Movie) {
            title.text = item.titleM
            rank.text = item.rankM
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.imageM).into(image)

            itemView.setOnClickListener {
                popularItemClick.onClick(it)
            }
        }

    }
}