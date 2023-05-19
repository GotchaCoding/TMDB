package org.techtown.diffuser.fragment.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.activity.moreview.viewHolder.CommonMoreViewHolder
import org.techtown.diffuser.con.Constants.VIEW_TYPE_COMMON_MORE
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.FailModel
import org.techtown.diffuser.model.Movie

class SearchAdapter(itemClickListener: (View, Int, Movie?, TheMore?) -> Unit) :
    BaseAdapter(itemClickListener) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        Log.e("kmh!!!", "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_COMMON_MORE -> {
                val itemView = inflater.inflate(R.layout.item_themore, parent, false)
                CommonMoreViewHolder(itemView, itemClickListener)
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
        }
        if (holder is FailViewHolder) {
            holder.setItem(itemModel as FailModel)
        }
    }
}