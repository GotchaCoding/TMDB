package org.techtown.diffuser.activity.moreview.popular

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.activity.moreview.viewHolder.CommonMoreViewHolder
import org.techtown.diffuser.constants.Constants.VIEW_TYPE_COMMON_MORE
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.FailModel
import org.techtown.diffuser.model.Movie

class PopularMoreAdapter( itemClickListener: (View, Int, Movie?, TheMore?) -> Unit) : BaseAdapter(itemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.e("kmh!!!" , "onCreateViewHolder" )
        return when (viewType) {
            VIEW_TYPE_COMMON_MORE -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_themore, parent, false)
                return CommonMoreViewHolder(itemView, itemClickListener)
            }
            else -> {
                super.onCreateViewHolder(parent, viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.e("kmh!!!" , "onBindViewHolder" )
        if (holder is CommonMoreViewHolder) {
            holder.setItem(currentList[position] as Movie)
        }

        if (holder is FailViewHolder) {
            holder.setItem(currentList[position] as FailModel)
        }

    }
}

