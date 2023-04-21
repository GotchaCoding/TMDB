//package org.techtown.diffuser.fragment.search
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import org.techtown.diffuser.R
//import org.techtown.diffuser.activity.moreview.BottomLoadingViewHolder
//import org.techtown.diffuser.model.ItemModel
//
//class SearchAdapter : ListAdapter<ItemModel, SearchAdapter.SearchViewHolder>(diffUtil_search){
//
//    class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
//        var editText : EditText
//        var btn : Button
//        init {
//            editText = itemView.findViewById(R.id.edtSearch)
//            btn = itemView.findViewById(R.id.btnSearch)
//        }
//
//        fun setItem() {
//
//        }
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val itemView = inflater.inflate(R.layout.item_bottom_loading, parent, false)
//        return SearchViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
//        holder.setItem()
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return currentList[position].viewType
//    }
//}
//
//
//
//    val diffUtil_search = object : DiffUtil.ItemCallback<ItemModel>() {
//        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
//            return oldItem.equals(newItem)
//        }
//    }
