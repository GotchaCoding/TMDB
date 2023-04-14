package org.techtown.diffuser.activity.moreview.popular

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
import org.techtown.diffuser.fragment.home.HomeAdapter
import org.techtown.diffuser.fragment.home.HorizontalPopularMoviesRecyclerAdapter
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.response.pupular.ResultPopular
import org.w3c.dom.Text

class PopularMoreAdapter : ListAdapter<ItemModel, RecyclerView.ViewHolder>(diffUtil3) {


    class MoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: ImageView
        var text: TextView

        init {
            image = itemView.findViewById(R.id.imgMore)
            text = itemView.findViewById(R.id.tvMore)
        }

        fun setItem(item: Movie) {
            Log.d("4.14", "setItem 메서드")
            text.text = item.title
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.imagePoster)
                .into(image)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("4.14", "onCreateViewHolder 메서드")
        when (viewType) {
            Constants.VIEW_TYPE_BOTTOM_MODEL -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_bottom_loading, parent, false)
                return BottomLoadingViewHolder(itemView)
            }
            HomeAdapter.VIEW_TYPE_POPULAR_MOVIE -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_themore, parent, false)
                return MoreViewHolder(itemView)
            }
            else -> {
                throw Exception()
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("4.14", "onBindViewHolder 메서드")
        if(holder is MoreViewHolder) {
            holder.setItem(currentList[position] as Movie)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

}

val diffUtil3 = object : DiffUtil.ItemCallback<ItemModel>() {
    override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
        return oldItem.equals(newItem)
    }

}