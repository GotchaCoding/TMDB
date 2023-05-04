package org.techtown.diffuser.activity.moreview.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.diffuser.R
import org.techtown.diffuser.model.Movie

class CommonMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var image: ImageView = itemView.findViewById(R.id.imgMore)
    private var title: TextView = itemView.findViewById(R.id.tvMoreTitle)
    private var date: TextView = itemView.findViewById(R.id.tvMoreDate)
    private var contents: TextView = itemView.findViewById(R.id.tvMoreContent)

    fun setItem(item: Movie) {
        title.text = item.title
        Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.imagePoster)
            .into(image)
        date.text = item.rank
        contents.text = item.overView
    }
}