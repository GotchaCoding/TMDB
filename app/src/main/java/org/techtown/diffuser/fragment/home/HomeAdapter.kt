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
import org.techtown.diffuser.response.ResultPopular

class HomeAdapter : RecyclerView.Adapter<HomeViewHolder>() {
    var items: ArrayList<Movie> = java.util.ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_popularmovie, parent, false)
        return HomeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addMovie(item: List<Movie>) {
        val positionStart : Int = this.items.size +1
        this.items.addAll(item)
        notifyItemRangeChanged(positionStart, items.size)
    }

}

class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
        Glide.with(itemView).load("https://image.tmdb.org/t/p/w500"+item.imageM).into(image)

    }

}