package org.techtown.diffuser.activity.moreview.nowplay

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
import org.techtown.diffuser.activity.moreview.BottomLoadingViewHolder
import org.techtown.diffuser.activity.moreview.popular.Constants
import org.techtown.diffuser.fragment.home.HomeAdapter
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie

class NowMoreAdapter : ListAdapter<ItemModel, RecyclerView.ViewHolder>(diffUtil4) {

    class NowMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var title: TextView
        var date: TextView
        var contents: TextView

        init {
            image = itemView.findViewById(R.id.imgMore)
            title = itemView.findViewById(R.id.tvMoreTitle)
            date = itemView.findViewById(R.id.tvMoreDate)
            contents = itemView.findViewById(R.id.tvMoreContent)
        }

        fun setItem(item: Movie) {
            Log.d("4.20" , "setItem")
            title.text = item.title
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.imagePoster)
                .into(image)
            date.text = item.rank
            contents.text = item.overView

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("4.20" , "onCreateViewHolder")
        Log.d("4.20" , "viewType : $viewType")
        when (viewType) {
            Constants.VIEW_TYPE_BOTTOM_MODEL -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_bottom_loading, parent, false)
                return BottomLoadingViewHolder(itemView)
            }
            HomeAdapter.VIEW_TYPE_NOW_MOVIE -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_themore, parent, false)
                return NowMoreViewHolder(itemView)
            }
            else -> {
                throw Exception()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if (holder is NowMoreViewHolder) {
           holder.setItem(currentList[position] as Movie)
       }
    }

//    override fun getItemCount(): Int {
//        return currentList.size
//    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }


}

val diffUtil4 = object : DiffUtil.ItemCallback<ItemModel>() {
    override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
        Log.d("4.20" , "areItemsTheSame")
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
        Log.d("4.20" , "areContentsTheSame")
        return oldItem.equals(newItem)
    }

}