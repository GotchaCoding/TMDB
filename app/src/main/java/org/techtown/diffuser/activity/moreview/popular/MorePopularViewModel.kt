package org.techtown.diffuser.activity.moreview.popular

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.techtown.diffuser.Repository
import org.techtown.diffuser.Resource
import org.techtown.diffuser.activity.moreview.popular.BottomLoadingModel.id
import org.techtown.diffuser.activity.moreview.popular.BottomLoadingModel.viewType
import org.techtown.diffuser.fragment.home.HomeAdapter
import org.techtown.diffuser.fragment.home.HomeFragment
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie
import javax.inject.Inject

@HiltViewModel
class MorePopularViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {
//    object BottomLoadingModel : ItemModel(id = -2L, viewType = -3)

    private val _items: MutableLiveData<List<ItemModel>> =
        MutableLiveData(listOf())
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

        Log.d("4.14", "scrolltest")
        repository
            .getPopular(page)
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
                                viewType = HomeAdapter.VIEW_TYPE_POPULAR_MOVIE,
                                id = it.id
                            )
                        }
                        page++
                        _items.value = pureItems() + list
                    }
                    is Resource.Fail -> {

//
                    }
                }
            }.launchIn(viewModelScope)
    }
}