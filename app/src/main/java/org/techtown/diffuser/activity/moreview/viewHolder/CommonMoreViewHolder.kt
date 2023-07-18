package org.techtown.diffuser.activity.moreview.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.databinding.ItemThemoreBinding
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.Movie

class CommonMoreViewHolder(
    val binding: ItemThemoreBinding,
    private val itemClickListener: (View, Int, Movie?, TheMore) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun setItem(item: Movie) {
        binding.movieItem = item

        itemView.setOnClickListener {
            itemClickListener(it, item.viewType, item, TheMore.THEMORE_POPULAR)
        }
    }
}