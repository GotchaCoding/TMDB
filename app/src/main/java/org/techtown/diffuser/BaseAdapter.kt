package org.techtown.diffuser

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.activity.moreview.BottomLoadingViewHolder
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.databinding.ItemFailureBinding
import org.techtown.diffuser.fragment.ItemClickListener
import org.techtown.diffuser.model.FailModel
import org.techtown.diffuser.model.ItemModel

abstract class BaseAdapter(
    protected val itemClickListener: ItemClickListener
) : ListAdapter<ItemModel, RecyclerView.ViewHolder>(diffUtil9) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Constants.VIEW_TYPE_BOTTOM_MODEL -> {
                val itemView = inflater.inflate(R.layout.item_bottom_loading, parent, false)
                BottomLoadingViewHolder(itemView)
            }

            Constants.VIEW_TYPE_FAIL -> {
                val binding: ItemFailureBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_failure, parent, false)
                FailViewHolder(binding, itemClickListener)
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
        val binding: ItemFailureBinding,
        private val itemClickListener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: FailModel) {
            with(binding) {
                if (item.viewType == Constants.VIEW_TYPE_FAIL) {
                    tvFail.setOnClickListener {
                        Log.e("kmh!!!", "FailViewHolder setItem : fail.setOnclickListner")
                        itemClickListener.onItemClick(
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
            return oldItem.equals(newItem)
        }
    }