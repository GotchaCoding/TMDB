package org.techtown.diffuser.activity.detailpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.constants.Constants.VIEW_TYPE_DETAIL_BACKGROND
import org.techtown.diffuser.databinding.ItemDetailCastBinding
import org.techtown.diffuser.databinding.ItemDetailImageBinding
import org.techtown.diffuser.databinding.ItemTitlepopualrBinding
import org.techtown.diffuser.fragment.ItemClickListener
import org.techtown.diffuser.model.TitleModel
import org.techtown.diffuser.model.WrappingDetailModel

class DetailAdapter(itemClickListener: ItemClickListener) :
    BaseAdapter(itemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            Constants.VIEW_TYPE_DETAIL_BACKGROND -> {
                val binding: ItemDetailImageBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_detail_image, parent, false)
                return BackImageViewHolder(binding, itemClickListener)
            }

            Constants.VIEW_TYPE_DETAIL_CASTING -> {
                val binding: ItemDetailCastBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_detail_cast, parent, false)
                return CastViewHolder(binding, itemClickListener)
            }

            Constants.VIEW_TYPE_DETAIL_TITLE -> {
                val binding : ItemTitlepopualrBinding = DataBindingUtil.inflate(inflater,  R.layout.item_titlepopualr, parent, false)
                return TitleViewHolder(binding)
            }

            else -> {
                throw Exception()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemModel = currentList[position]
        when (itemModel.viewType) {
            Constants.VIEW_TYPE_DETAIL_BACKGROND -> {
                if (itemModel is WrappingDetailModel) {
                    (holder as BackImageViewHolder).apply {
                        setItem(itemModel)
                        binding.executePendingBindings()
                    }
                }
            }

            Constants.VIEW_TYPE_DETAIL_CASTING -> {
                if (itemModel is WrappingDetailModel) {
                    (holder as CastViewHolder).apply {
                    setItem(itemModel)
                    binding.executePendingBindings()
                    }
                }
            }

            Constants.VIEW_TYPE_DETAIL_TITLE -> {
                if (itemModel is TitleModel) {
                    (holder as TitleViewHolder).apply {
                    setItem(itemModel)
                    binding.executePendingBindings()
                    }
                }
            }
        }
    }


    class BackImageViewHolder(
        val binding: ItemDetailImageBinding,
        val itemClickListener:ItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            with(binding) {
                vLoading.setAnimation(R.raw.loading)
                vLoading.repeatCount = -1
                vLoading.playAnimation()
            }
        }

        fun setItem(item: WrappingDetailModel) {
            with(binding) {
                detailTopModelItem = item.detailTopModel

                if (item.isLoading) { //로딩중
                    vLoading.isVisible = true
                    viewFailure.isVisible = false
                } else if (item.detailTopModel?.isFailure == false) { //성공
                    vLoading.isVisible = false
                    viewFailure.isVisible = false
                } else {//실패
                    viewFailure.isVisible = true
                    vLoading.isVisible = false
                    viewFailure.setOnClickListener {
                        itemClickListener.onItemClick(
                            it,
                            VIEW_TYPE_DETAIL_BACKGROND,
                            null,
                            null
                        )
                    }
                }
            }
        }
    }

    class CastViewHolder(
        val binding: ItemDetailCastBinding,
        val itemClickListener:ItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        var adapter =
            CastAdapter(itemClickListener)

        init {
            with(binding) {
                vLoading.setAnimation(R.raw.loading)
                vLoading.repeatCount = -1
                vLoading.playAnimation()
                rvCast.adapter = adapter
                rvCast.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }

        fun setItem(item: WrappingDetailModel) {
            with(binding) {
                if (item.isFailure) {
                    onFailure.isVisible = true
                    vLoading.isVisible = item.isLoading
                } else {
                    onFailure.isVisible = false
                    vLoading.isVisible = item.isLoading

                    if (item.castModel != null) {
                        adapter.submitList(item.castModel.castList)
                    }
                }
//                onFailure.setOnClickListener {
//                    itemClickListener.onItemClick(it, item.viewType, null, null)
//                }
            }
            binding.itemClickListener = itemClickListener
        }
    }

}
