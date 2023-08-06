package org.techtown.diffuser.activity.moreview.viewHolder

import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.databinding.ItemThemoreBinding
import org.techtown.diffuser.fragment.ItemClickListener
import org.techtown.diffuser.model.Movie

class CommonMoreViewHolder(
    val binding: ItemThemoreBinding,
    private val itemClickListener: ItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun setItem(item: Movie) {
        binding.item = item
        binding.itemClickListener = itemClickListener
    }
}