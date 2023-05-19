package org.techtown.diffuser.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.diffuser.R
import org.techtown.diffuser.model.Movie

class GridMovieAdapter() :
    ListAdapter<Movie, GridMovieAdapter.GirdMovieMultiViewHolder>(diffUtil_grid) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GirdMovieMultiViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_grid, parent, false)
        return GirdMovieMultiViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GirdMovieMultiViewHolder, position: Int) {
        val movie = currentList[position]
        holder.setItem(movie)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class GirdMovieMultiViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
//        var title: TextView = itemView.findViewById(R.id.tvGrid)
        var image: ImageView = itemView.findViewById(R.id.imgGrid)
//        var rank: TextView = itemView.findViewById(R.id.tvGridRank)

        fun setItem(item: Movie) {
//            title.text = item.title
//            rank.text = item.rank
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.imagePoster)
                .into(image)
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