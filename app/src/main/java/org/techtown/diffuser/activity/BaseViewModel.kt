package org.techtown.diffuser.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.techtown.diffuser.activity.moreview.popular.BottomLoadingModel
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie

open class BaseViewModel : ViewModel(){

    protected val _items: MutableLiveData<List<ItemModel>> = MutableLiveData(listOf())
    val items: LiveData<List<ItemModel>> = _items
    var page: Int = 1
    var editText : String = "슬램덩크"

    fun pureItems(): List<ItemModel> {
        return _items.value!!.filter {
            it is Movie
        }
    }
    fun isLoading() : Boolean {
        return _items.value!!.filterIsInstance<BottomLoadingModel>().isNotEmpty()
    }
}