package org.techtown.diffuser.activity.moreview.comming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.activity.moreview.viewHolder.CommonMoreViewHolder
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.FailModel
import org.techtown.diffuser.model.Movie

class CommingMoreAdapter(itemClickListener: (View, Int, Movie?, TheMore?) -> Unit) :
    BaseAdapter(itemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.VIEW_TYPE_UPCOMMING -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_themore, parent, false)
                CommonMoreViewHolder(itemView, itemClickListener)
            }
            else -> {
                super.onCreateViewHolder(parent, viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CommonMoreViewHolder) {
            holder.setItem(currentList[position] as Movie)
        }

        if (holder is FailViewHolder) {
            holder.setItem(currentList[position] as FailModel)
        }
    }

}

