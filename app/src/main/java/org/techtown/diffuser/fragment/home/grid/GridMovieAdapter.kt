package org.techtown.diffuser.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.diffuser.R
import org.techtown.diffuser.model.Movie

class GridMovieAdapter(private val itemClickListener: (View, Int, Movie?, TheMore?) -> Unit) :
    ListAdapter<Movie, GridMovieAdapter.GirdMovieMultiViewHolder>(diffUtil_grid) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GirdMovieMultiViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_grid_movie, parent, false)
        return GirdMovieMultiViewHolder(itemView, itemClickListener)
    }

    override fun onBindViewHolder(holder: GirdMovieMultiViewHolder, position: Int) {
        val movie = currentList[position]
        holder.setItem(movie)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class GirdMovieMultiViewHolder(
        itemView: View,
        private val itemClickListener: (View, Int, Movie?, TheMore?) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.imgGrid)

        fun setItem(item: Movie) {
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.imagePoster)
                .into(image)

            itemView.setOnClickListener {
                itemClickListener(it, item.viewType, item, null)
            }
        }
    }
}

val diffUtil_grid = object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id   // === hash 비교.       == 안에잇는 내용 컨텐츠 비교
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.equals(newItem)
    }
}