package org.techtown.diffuser.fragment.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.activity.moreview.viewHolder.CommonMoreViewHolder
import org.techtown.diffuser.constants.Constants.VIEW_TYPE_COMMON_MORE
import org.techtown.diffuser.constants.Constants.VIEW_TYPE_EMPTY
import org.techtown.diffuser.databinding.ItemThemoreBinding
import org.techtown.diffuser.fragment.ItemClickListener
import org.techtown.diffuser.model.FailModel
import org.techtown.diffuser.model.Movie

class SearchAdapter(itemClickListener: ItemClickListener) :
    BaseAdapter(itemClickListener) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_COMMON_MORE -> {
                val binding: ItemThemoreBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_themore, parent, false)
                CommonMoreViewHolder(binding, itemClickListener)
            }

            VIEW_TYPE_EMPTY -> {
                val itemView = inflater.inflate(R.layout.item_empty, parent, false)
                EmptyViewholder(itemView)
            }

            else -> {
                super.onCreateViewHolder(parent, viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("kmh!!!", "onBindViewHolder")
        val itemModel = currentList[position]
        if (holder is CommonMoreViewHolder) {
            holder.setItem(itemModel as Movie)
            holder.binding.executePendingBindings()
        }
        if (holder is FailViewHolder) {
            holder.setItem(itemModel as FailModel)
            holder.binding.executePendingBindings()
        }

    }

    class EmptyViewholder(itemView: View) : RecyclerView.ViewHolder(itemView)
}