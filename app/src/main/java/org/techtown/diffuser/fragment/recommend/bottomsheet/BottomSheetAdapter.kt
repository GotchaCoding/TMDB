package org.techtown.diffuser.fragment.recommend.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.databinding.ItemBottomsheetBigRecyclerBinding
import org.techtown.diffuser.databinding.ItemBottomsheetRecyclerBinding
import org.techtown.diffuser.fragment.ItemClickListener
import org.techtown.diffuser.model.Movie

class BottomSheetAdapter(
    itemClickListener: ItemClickListener
) : BaseAdapter(itemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Constants.VIEW_TYPE_BOTTOMSHEET -> {
                val binding: ItemBottomsheetRecyclerBinding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.item_bottomsheet_recycler,
                    parent,
                    false
                )
                BottomSheetViewHolder(binding, itemClickListener)
            }

            Constants.VIEW_TYPE_BOTTOM_BIGPIC -> {
                val binding: ItemBottomsheetBigRecyclerBinding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.item_bottomsheet_big_recycler,
                    parent,
                    false
                )
                BottomSheetBigViewHolder(binding, itemClickListener)
            }

            else -> {
                super.onCreateViewHolder(parent, viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList[position]
        if (holder is BottomSheetViewHolder) {
            holder.setItem(item as Movie)
        }
        if (holder is BottomSheetBigViewHolder) {
            holder.setItem(item as Movie)
        }
    }
}


class BottomSheetViewHolder(
    val binding: ItemBottomsheetRecyclerBinding,
    private val itemClickListener: ItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun setItem(item: Movie) {
        binding.item = item
        binding.itemClickListener = itemClickListener
    }
}

class BottomSheetBigViewHolder(
    val binding: ItemBottomsheetBigRecyclerBinding, private val itemClickListener: ItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun setItem(item: Movie) {
        binding.item = item
        binding.itemClickListener = itemClickListener
    }
}