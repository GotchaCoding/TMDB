package org.techtown.diffuser.activity.moreview.nowplay

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.techtown.diffuser.Repository
import org.techtown.diffuser.Resource
import org.techtown.diffuser.activity.BaseViewModel
import org.techtown.diffuser.activity.moreview.popular.BottomLoadingModel
import org.techtown.diffuser.fragment.home.HomeAdapter
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie
import javax.inject.Inject

@HiltViewModel
class MoreNowplayViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel() {

    fun fetch() {
        if(isLoading()) return

        repository
            .getNowPlay(page)
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
                                viewType = HomeAdapter.VIEW_TYPE_NOW_MOVIE,
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