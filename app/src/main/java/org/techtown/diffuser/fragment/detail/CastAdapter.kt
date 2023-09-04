package org.techtown.diffuser.fragment.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.databinding.ItemCastBinding
import org.techtown.diffuser.fragment.ItemClickListener
import org.techtown.diffuser.model.CastRv

class CastAdapter(itemClickListener: ItemClickListener) :
    BaseAdapter(itemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding:ItemCastBinding = DataBindingUtil.inflate(inflater,R.layout.item_cast, parent, false)
        return CastingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemModel = currentList[position]
        if (holder is CastingViewHolder && itemModel is CastRv) {
            holder.apply {
            setItem(itemModel)
            binding.executePendingBindings()
            }
        }
    }

    class CastingViewHolder(val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(item: CastRv) {
            binding.castRVItems = item
        }
    }
}