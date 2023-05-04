package org.techtown.diffuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.activity.moreview.BottomLoadingViewHolder
import org.techtown.diffuser.activity.moreview.popular.Constants
import org.techtown.diffuser.model.ItemModel

abstract class BaseAdapter : ListAdapter<ItemModel, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            Constants.VIEW_TYPE_BOTTOM_MODEL -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_bottom_loading, parent, false)
                BottomLoadingViewHolder(itemView)
            }
            else -> {
                throw Exception()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }
}

val diffUtil = object : DiffUtil.ItemCallback<ItemModel>() {  //두개의 데이터 목록을 가지고 차이점이 있는 아이템만 자동 업데이트
    override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {  // 두 객체가 동일한가?
        return oldItem.id == newItem.id  //아이템 일치여부는 고유 id 값을 비교해서 판독
    }

    override fun areContentsTheSame(
        oldItem: ItemModel,
        newItem: ItemModel
    ): Boolean {  //두 아이템이 동일한가 ?
        return oldItem.equals(newItem)  // deep copy를 통해 새로운 공간을 확보해 완전히 복사하여 비교. (리스트 주소값이 동일하면 업데이트 x)
    }
}