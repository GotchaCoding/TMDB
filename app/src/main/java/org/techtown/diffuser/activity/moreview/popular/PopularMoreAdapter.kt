package org.techtown.diffuser.activity.moreview.popular

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.activity.moreview.viewHolder.CommonMoreViewHolder
import org.techtown.diffuser.constants.Constants.VIEW_TYPE_COMMON_MORE
import org.techtown.diffuser.databinding.ItemThemoreBinding
import org.techtown.diffuser.fragment.ItemClickListener
import org.techtown.diffuser.model.FailModel
import org.techtown.diffuser.model.Movie

class PopularMoreAdapter( itemClickListener: ItemClickListener) : BaseAdapter(itemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.e("kmh!!!" , "onCreateViewHolder" )
        return when (viewType) {
            VIEW_TYPE_COMMON_MORE -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding : ItemThemoreBinding = DataBindingUtil.inflate(inflater, R.layout.item_themore, parent, false)
                return CommonMoreViewHolder(binding, itemClickListener)
            }
            else -> {
                super.onCreateViewHolder(parent, viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.e("kmh!!!" , "onBindViewHolder" )
        if (holder is CommonMoreViewHolder) {
            holder.apply {
            setItem(currentList[position] as Movie)
            binding.executePendingBindings()
            }
        }

        if (holder is FailViewHolder) {
            holder.apply {
            setItem(currentList[position] as FailModel)
            binding.executePendingBindings()
            }
        }

    }
}

