package org.techtown.diffuser.activity.moreview.nowplay

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.techtown.diffuser.ServiceRepository
import org.techtown.diffuser.Resource
import org.techtown.diffuser.activity.BaseViewModel
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.model.BottomLoadingModel
import org.techtown.diffuser.model.FailModel
import org.techtown.diffuser.model.Movie
import javax.inject.Inject

@HiltViewModel
class MoreNowplayViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val serviceRepository: ServiceRepository
) : BaseViewModel() {

    fun fetch() {
        if(isLoading()) return

        Log.e("kmh!!!", "page count : $page")
        serviceRepository
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
                                viewType = Constants.VIEW_TYPE_NOW_MOVIE,
                                id = it.id
                            )
                        }
                        page++
                        _items.value = pureItems() + list
                    }
                    is Resource.Fail -> {
                        _items.value = listOf(FailModel)
                    }
                }
            }.launchIn(viewModelScope)
    }
}