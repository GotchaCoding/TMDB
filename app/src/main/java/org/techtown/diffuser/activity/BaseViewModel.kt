package org.techtown.diffuser.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.techtown.diffuser.model.BottomLoadingModel
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie

open class BaseViewModel : ViewModel(){  // 뷰모델의 중복내용 base 모델화.

    protected val _items: MutableLiveData<List<ItemModel>> = MutableLiveData(listOf())  //외부상황에 영향을 받지 않게 Base뷰모델을 상속하는 뷰모델 내부에서 값을 수정하며 사용하기위해  Mutable은 protected로 설정
    val items: LiveData<List<ItemModel>> = _items  //LiveData로 설정한 변수는 외부에서 수정불가하고 observe로 감시만 가능하게.
    var page: Int = 1

    fun pureItems(): List<ItemModel> {
        return _items.value!!.filterIsInstance<Movie>()
    }
    fun isLoading() : Boolean { //BottomLoadingModel 객체가 존재하면 true 아니면 false
        return _items.value!!.filterIsInstance<BottomLoadingModel>().isNotEmpty()
    }
}