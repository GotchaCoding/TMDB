package org.techtown.diffuser.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.diffuser.R
import org.techtown.diffuser.model.Movie

class HorizontalPopularMoviesRecyclerAdapter(private val itemClickListener: (View, Int, Movie?) -> Unit) :
    RecyclerView.Adapter<HorizontalPopularMoviesRecyclerAdapter.MovieViewHolder>() {

    var items: List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_popularmovie, parent, false)
        return MovieViewHolder(itemView, itemClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setItem(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setMovies(movies: List<Movie>) {
        items = movies
        notifyDataSetChanged()
    }

    class MovieViewHolder(
        itemView: View,
        private val ItemClickListener: (View, Int, Movie?) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {

        var title: TextView = itemView.findViewById(R.id.tvPopularTitle)
        var rank: TextView = itemView.findViewById(R.id.tvPopularRank)
        var image: ImageView = itemView.findViewById(R.id.imagePopular)

        fun setItem(item: Movie) {
            title.text = item.title
            rank.text = item.rank
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.imagePoster)
                .into(image)

            itemView.setOnClickListener {
                ItemClickListener(it, item.viewType, item)
            }
        }
    }
}