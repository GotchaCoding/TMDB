package org.techtown.diffuser.fragment.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.activity.moreview.popular.Constants.VIEW_TYPE_COMMON_MORE
import org.techtown.diffuser.activity.moreview.popular.Constants.VIEW_TYPE_FAIL
import org.techtown.diffuser.activity.moreview.viewHolder.CommonMoreViewHolder
import org.techtown.diffuser.model.Movie

class SearchAdapter : BaseAdapter() {

    class FailViewHolder(itemView: View) : ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_COMMON_MORE -> {
                val itemView = inflater.inflate(R.layout.item_themore, parent, false)
                CommonMoreViewHolder(itemView)
            }
            VIEW_TYPE_FAIL -> {
                val itemView = inflater.inflate(R.layout.item_failure, parent, false)
                FailViewHolder(itemView)
            }
            else -> {
                super.onCreateViewHolder(parent, viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemModel = currentList[position]
        if (holder is CommonMoreViewHolder) {
            holder.setItem(itemModel as Movie)
        }
    }
}