package org.techtown.diffuser.fragment.search

import android.util.Log
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
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie

class SearchAdapter : ListAdapter<ItemModel, SearchAdapter.SearchViewHolder>(diffUtil_search){

    class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.imgMore)
        var title: TextView = itemView.findViewById(R.id.tvMoreTitle)
        var date: TextView = itemView.findViewById(R.id.tvMoreDate)
        var contents: TextView = itemView.findViewById(R.id.tvMoreContent)

        fun setItem(item: Movie) {
            Log.d("4.22" , "setItem")
            title.text = item.title
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.imagePoster)
                .into(image)
            date.text = item.rank
            contents.text = item.overView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_themore, parent, false)
        return SearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
            holder.setItem(currentList[position] as Movie)
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }
}

    val diffUtil_search = object : DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem.equals(newItem)
        }
    }
