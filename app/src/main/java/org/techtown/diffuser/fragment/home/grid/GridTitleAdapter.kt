package org.techtown.diffuser.fragment.home.grid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.diffuser.R
import org.techtown.diffuser.fragment.home.HomeAdapter
import org.techtown.diffuser.fragment.home.diffUtil_grid
import org.techtown.diffuser.model.Movie

class GridTitleAdapter : RecyclerView.Adapter<GridTitleAdapter.GridMultiTitleViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GridMultiTitleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_grid_title, parent, false)
        return GridMultiTitleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GridMultiTitleViewHolder, position: Int) {
        holder.setItem()
    }

    override fun getItemCount(): Int {
        return 1
    }

    fun setItem() {
        notifyDataSetChanged()
    }


    class GridMultiTitleViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        var tvGrid: TextView = itemView.findViewById(R.id.tvGridTitle)

        fun setItem() {
            tvGrid.text = "추천영화"
        }
    }
}
