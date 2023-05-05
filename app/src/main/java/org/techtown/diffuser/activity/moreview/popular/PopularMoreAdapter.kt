package org.techtown.diffuser.activity.moreview.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.activity.moreview.viewHolder.CommonMoreViewHolder
import org.techtown.diffuser.con.Constants
import org.techtown.diffuser.con.Constants.KEY_SEARCH_FAIL_MODEL_ID
import org.techtown.diffuser.con.Constants.VIEW_TYPE_COMMON_MORE
import org.techtown.diffuser.con.Constants.VIEW_TYPE_FAIL
import org.techtown.diffuser.fragment.home.HomeAdapter
import org.techtown.diffuser.fragment.search.SearchAdapter
import org.techtown.diffuser.model.FailModel
import org.techtown.diffuser.model.Movie

class PopularMoreAdapter : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_COMMON_MORE -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_themore, parent, false)
                return CommonMoreViewHolder(itemView)
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
    }
}

