package org.techtown.diffuser.activity.moreview.comming

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
import org.techtown.diffuser.activity.moreview.popular.PopularMoreAdapter
import org.techtown.diffuser.fragment.home.HomeAdapter
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie

class CommingMoreAdapter : ListAdapter<ItemModel, RecyclerView.ViewHolder>(diffUtil_Com) {

    class MoreCommingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
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
            title.text = item.title
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.imagePoster)
                .into(image)
            date.text = item.rank
            contents.text = item.overView

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            Constants.VIEW_TYPE_BOTTOM_MODEL -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_bottom_loading, parent, false)
                return BottomLoadingViewHolder(itemView)
            }
            HomeAdapter.VIEW_TYPE_UPCOMMING -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_themore, parent, false)
                return MoreCommingViewHolder(itemView)
            }
            else -> {
                throw Exception()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MoreCommingViewHolder) {
            holder.setItem(currentList[position] as Movie)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }


}

val diffUtil_Com = object : DiffUtil.ItemCallback<ItemModel>() {
    override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
        return oldItem.equals(newItem)
    }

}