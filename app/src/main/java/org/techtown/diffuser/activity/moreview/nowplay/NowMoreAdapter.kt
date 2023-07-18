package org.techtown.diffuser.activity.moreview.nowplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.activity.moreview.viewHolder.CommonMoreViewHolder
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.databinding.ItemThemoreBinding
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.FailModel
import org.techtown.diffuser.model.Movie

class NowMoreAdapter(itemClickListener: (View, Int, Movie?, TheMore?) -> Unit) :
    BaseAdapter(itemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.VIEW_TYPE_NOW_MOVIE -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding : ItemThemoreBinding = DataBindingUtil.inflate(inflater, R.layout.item_themore, parent, false)
                CommonMoreViewHolder(binding, itemClickListener)
            }
            else -> {
                super.onCreateViewHolder(parent, viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
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

