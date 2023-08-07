package org.techtown.diffuser.fragment.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.databinding.ItemRecommendBinding
import org.techtown.diffuser.databinding.ItemTitlepopualrBinding
import org.techtown.diffuser.fragment.ItemClickListener
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.model.TitleModel

class RecommendAdapter(
    itemClickListener: ItemClickListener
) : BaseAdapter(itemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Constants.VIEW_TYPE_RECOMMEND_TITLE -> {
                val binding: ItemTitlepopualrBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_titlepopualr, parent, false)
                RecommendTitleViewHolder(binding, itemClickListener)
            }

            Constants.VIEW_TYPE_RECOMMEND_ITEM -> {
                val binding: ItemRecommendBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_recommend, parent, false)
                RecommendViewHolder(binding, itemClickListener)
            }

            else -> {
                super.onCreateViewHolder(parent, viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList[position]
        if (holder is RecommendViewHolder) {
            holder.apply {
                setItem(item as Movie)
                binding.executePendingBindings()
            }
        } else if (holder is RecommendTitleViewHolder) {
            holder.apply {
                setItem(item as TitleModel)
                binding.executePendingBindings()
            }
        }
    }

    class RecommendTitleViewHolder(
        val binding: ItemTitlepopualrBinding,
        private val itemClickListener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {


        fun setItem(item: TitleModel) {
            with(binding) {
                tvTitle.text = item.title
            }
                binding.itemClickListener= itemClickListener
                binding.titleModel = item
        }
    }

    class RecommendViewHolder(
        val binding: ItemRecommendBinding,
        private val itemClickListener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(item: Movie) {
            binding.item = item
            binding.itemClickListener = itemClickListener
        }
    }

}