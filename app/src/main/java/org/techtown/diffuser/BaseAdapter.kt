package org.techtown.diffuser

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.activity.moreview.BottomLoadingViewHolder
import org.techtown.diffuser.con.Constants
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.FailModel
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie

abstract class BaseAdapter(
    protected val itemClickListener: (View, Int, Movie?, TheMore?) -> Unit
) : ListAdapter<ItemModel, RecyclerView.ViewHolder>(diffUtil9) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            Constants.VIEW_TYPE_BOTTOM_MODEL -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_bottom_loading, parent, false)
                BottomLoadingViewHolder(itemView)
            }
            Constants.VIEW_TYPE_FAIL -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_failure, parent, false)
                return FailViewHolder(itemView, itemClickListener)
            }
            else -> {
                throw Exception("ViewHolder Exception")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }

    class FailViewHolder(
        itemView: View,
        private val itemClickListener: (View, Int, Movie?, TheMore?) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private var fail: TextView = itemView.findViewById(R.id.tvFail)

        fun setItem(item: FailModel) {
            if (item.viewType == Constants.VIEW_TYPE_FAIL) {
                Log.d("kmh!!!", "setItem test ")
                fail.setOnClickListener {
                    Log.d("kmh!!!", "setItem test2 ")
                    itemClickListener(
                        it,
                        item.viewType,
                        null,
                        null
                    )
                }
            }
        }
    }

}

val diffUtil9 =
    object : DiffUtil.ItemCallback<ItemModel>() {  //두개의 데이터 목록을 가지고 차이점이 있는 아이템만 자동 업데이트
        override fun areItemsTheSame(
            oldItem: ItemModel,
            newItem: ItemModel
        ): Boolean {  // 두 객체가 동일한가?
            return oldItem.id == newItem.id  //아이템 일치여부는 고유 id 값을 비교해서 판독
        }

        override fun areContentsTheSame(
            oldItem: ItemModel,
            newItem: ItemModel
        ): Boolean {  //두 아이템이 동일한가 ?
            return oldItem.equals(newItem)  // deep copy를 통해 새로운 공간을 확보해 완전히 복사하여 비교. (리스트 주소값이 동일하면 업데이트 x)
        }
    }