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

class HorizontalPopularMoviesRecyclerAdapter(private val itemClickListener: (View, Int, Movie?, TheMore) -> Unit) :
    RecyclerView.Adapter<HorizontalPopularMoviesRecyclerAdapter.MovieViewHolder>() {   // 제너럴 타입을   일반 리사이클러뷰처럼  커스텀뷰홀더 타입으로 지정.( 여러 뷰홀더를 안쓸꺼니까)

    var items: List<Movie> = listOf()  //어뎁터 내에 사용하는 리스트. Moive클래스 정보만 필요.

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
        private val itemClickListener: (View, Int, Movie?, TheMore) -> Unit
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
                itemClickListener(it, item.viewType, item, TheMore.THEMORE_POPULAR)
            }
        }
    }
}