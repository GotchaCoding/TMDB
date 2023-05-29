package org.techtown.diffuser.activity.detailpage

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.R
import org.techtown.diffuser.model.TitleModel

class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
    var tvMoreview: TextView = itemView.findViewById(R.id.tvMoreview)

    fun setItem(item: TitleModel) {
        tvTitle.text = item.title
        tvMoreview.text = ""
    }
}