package org.techtown.diffuser.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.R
import org.techtown.diffuser.databinding.ItemPopularmovieBinding
import org.techtown.diffuser.model.Movie

class HorizontalPopularMoviesRecyclerAdapter(private val itemClickListener: (View, Int, Movie?, TheMore) -> Unit) :
    RecyclerView.Adapter<HorizontalPopularMoviesRecyclerAdapter.MovieViewHolder>() {   // 제너럴 타입을   일반 리사이클러뷰처럼  커스텀뷰홀더 타입으로 지정.( 여러 뷰홀더를 안쓸꺼니까)

    var items: List<Movie> = listOf()  //어뎁터 내에 사용하는 리스트. Moive클래스 정보만 필요.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemPopularmovieBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_popularmovie,
            parent,
            false
        )  //Todo 리턴 타입 확인
//        val itemView = inflater.inflate(R.layout.item_popularmovie, parent, false)
        return MovieViewHolder(binding, itemClickListener)
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
        val binding: ItemPopularmovieBinding,
        private val itemClickListener: (View, Int, Movie?, TheMore) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: Movie) {
            with(binding){
                movieItem = item
                executePendingBindings()
            }
            itemView.setOnClickListener {
                itemClickListener(it, item.viewType, item, TheMore.THEMORE_POPULAR)
            }
        }
    }
}