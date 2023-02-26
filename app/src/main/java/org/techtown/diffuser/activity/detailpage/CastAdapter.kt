package org.techtown.diffuser.activity.detailpage

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
import org.techtown.diffuser.model.CastRv
import org.techtown.diffuser.model.HorizontalCastModel
import org.techtown.diffuser.model.ItemModel

class CastAdapter : ListAdapter<CastRv, CastAdapter.CastingViewHolder>(diffUtil2) {


    class CastingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgActor: ImageView
        var character: TextView
        var name: TextView

        init {
            imgActor = itemView.findViewById(R.id.img_cast)
            character = itemView.findViewById(R.id.tvCastCharacter)
            name = itemView.findViewById(R.id.tvCastName)
        }

        fun setItem(item: CastRv) {
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + item.imgActor)
                .circleCrop()
                .into(imgActor)
            character.text = item.castChracter
            name.text = item.castName
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_cast, parent, false)
        return CastingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CastingViewHolder, position: Int) {
        val castRvModel = currentList[position]
        holder.setItem(castRvModel)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

}

val diffUtil2 = object : DiffUtil.ItemCallback<CastRv>() {
    override fun areItemsTheSame(oldItem: CastRv, newItem: CastRv): Boolean {
        Log.e("kyh!!!","oldItem === newItem : ${oldItem === newItem}")
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CastRv, newItem: CastRv): Boolean {
        Log.e("kyh!!!","oldItem == newItem : ${oldItem.equals(newItem)}")
        return oldItem.equals(newItem)
    }
}
