package org.techtown.diffuser.activity.detailpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.diffuser.R
import org.techtown.diffuser.model.CastRv

class CastAdapter : RecyclerView.Adapter<CastAdapter.CastingViewHolder>() {
    var items: List<CastRv> = listOf()

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
        holder.setItem(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setCast(cast: List<CastRv>) {
        items = cast
        notifyDataSetChanged()
    }

}
