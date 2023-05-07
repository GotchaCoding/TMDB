package org.techtown.diffuser.activity.detailpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.CastRv
import org.techtown.diffuser.model.Movie

class CastAdapter(itemClickListener: (View, Int, Movie?, TheMore?) -> Unit) :
    BaseAdapter(itemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_cast, parent, false)
        return CastingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemModel = currentList[position]
        if (holder is CastingViewHolder && itemModel is CastRv) {
            holder.setItem(itemModel)
        }
    }

    class CastingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgActor: ImageView
        private val character: TextView
        private val name: TextView

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
}