package org.techtown.diffuser.fragment.detail

import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.databinding.ItemTitlepopualrBinding
import org.techtown.diffuser.model.TitleModel

class TitleViewHolder(val binding: ItemTitlepopualrBinding) : RecyclerView.ViewHolder(binding.root) {

    fun setItem(item: TitleModel) {
        binding.apply {
        tvTitle.text = item.title
        tvMoreview.text = ""
        }
    }
}