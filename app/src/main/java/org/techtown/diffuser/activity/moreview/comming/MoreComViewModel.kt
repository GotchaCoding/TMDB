package org.techtown.diffuser.activity.moreview.comming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.techtown.diffuser.Repository
import org.techtown.diffuser.Resource
import org.techtown.diffuser.activity.moreview.popular.BottomLoadingModel
import org.techtown.diffuser.fragment.home.HomeAdapter
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie
import javax.inject.Inject

@HiltViewModel
class MoreComViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _items: MutableLiveData<List<ItemModel>> = MutableLiveData(listOf())
    val items: LiveData<List<ItemModel>> = _items
    var page: Int = 1

    fun pureItems(): List<ItemModel> {
        return _items.value!!.filter {
            it is Movie
        }
    }
    fun isLoading() : Boolean {
        return _items.value!!.filterIsInstance<BottomLoadingModel>().isNotEmpty()
    }

    fun fetch() {
        if(isLoading()) return

        repository
            .getUpComming(page)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _items.value = pureItems() + BottomLoadingModel
                    }
                    is Resource.Success -> {
                        val response = result.model
                        val list = response.results.map {
                            Movie(
                                title = it.title,
                                rank = it.releaseDate,
                                imagePoster = it.posterPath,
                                overView = it.overview,
                                viewType = HomeAdapter.VIEW_TYPE_UPCOMMING,
                                id = it.id
                            )
                        }
                        page++
                        _items.value = pureItems() + list
                    }
                    is Resource.Fail -> {

                    }
                }
            }.launchIn(viewModelScope)
    }
}