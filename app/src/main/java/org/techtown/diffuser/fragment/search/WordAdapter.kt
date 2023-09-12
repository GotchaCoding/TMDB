package org.techtown.diffuser.fragment.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.R
import org.techtown.diffuser.databinding.ItemWordBinding
import org.techtown.diffuser.fragment.ItemClickListenerWord
import org.techtown.diffuser.room.Word

class WordAdapter(private val itemClickListener: ItemClickListenerWord) :
    ListAdapter<Word, WordViewholder>(diffUtil8) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewholder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemWordBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_word, parent, false)
        return WordViewholder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: WordViewholder, position: Int) {
        val current = getItem(position)
        holder.setItem(current)
    }
}


class WordViewholder(
    val binding: ItemWordBinding,
    private val itemClickListener : ItemClickListenerWord
) : RecyclerView.ViewHolder(binding.root) {
    fun setItem(item: Word) {
        binding.item = item
        binding.itemClickListener = itemClickListener
        binding.executePendingBindings()
    }
}

val diffUtil8 =
    object : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(
            oldItem: Word,
            newItem: Word
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Word,
            newItem: Word
        ): Boolean {
            return oldItem.equals(newItem)
        }
    }