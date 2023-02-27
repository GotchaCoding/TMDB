package org.techtown.diffuser.fragment.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.retrofit.RetrofitInterface
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val service: RetrofitInterface
) : ViewModel() {
    val items : MutableLiveData<List<ItemModel>> = MutableLiveData()
    fun test() {
        Log.e("test", " hiltViewmodel service : $service")
    }

}