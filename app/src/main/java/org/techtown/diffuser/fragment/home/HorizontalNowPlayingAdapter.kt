package org.techtown.diffuser.fragment.home

import android.util.Log
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
import org.techtown.diffuser.listener.PopularClickListener
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie

class HorizontalNowPlayingAdapter(private val ItemClickListener: PopularClickListener) :
    ListAdapter<Movie,NowMovieViewHolder>(diffUtil_movie) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_nowplaying, parent, false)
        return NowMovieViewHolder(itemView, ItemClickListener)
    }

    override fun onBindViewHolder(holder: NowMovieViewHolder, position: Int) {
        val movie = currentList[position]
        holder.setItem(movie)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


}
class NowMovieViewHolder(itemView: View, private val popularItemClick: PopularClickListener) :
    RecyclerView.ViewHolder(itemView) {

        var title: TextView
        var rank: TextView
        var image: ImageView


        init {
            title = itemView.findViewById(R.id.tvNowTitle)
            rank = itemView.findViewById(R.id.tvNowGenre)
            image = itemView.findViewById(R.id.imgNow)
        }

        fun setItem(item: Movie) {
            title.text = item.title
            rank.text = item.rank
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.imageDrop).into(image)

            itemView.setOnClickListener {
                popularItemClick.onClick(item)
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