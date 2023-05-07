package org.techtown.diffuser.activity.moreview.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.diffuser.R
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.Movie

class MoreCommingViewHolder(
    itemView: View,
    private val itemClickListener: (View, Int, Movie?, TheMore) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val image: ImageView = itemView.findViewById(R.id.imgMore)
    private val title: TextView = itemView.findViewById(R.id.tvMoreTitle)
    private val date: TextView = itemView.findViewById(R.id.tvMoreDate)
    private val contents: TextView = itemView.findViewById(R.id.tvMoreContent)

    fun setItem(item: Movie) {
        title.text = item.title
        Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.imagePoster)
            .into(image)
        date.text = item.rank
        contents.text = item.overView

        itemView.setOnClickListener {
            itemClickListener(it, item.viewType, item, TheMore.THEMORE_POPULAR)
        }
    }
}