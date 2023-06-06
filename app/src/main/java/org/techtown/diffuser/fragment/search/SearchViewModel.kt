package org.techtown.diffuser.fragment.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.techtown.diffuser.Repository
import org.techtown.diffuser.Resource
import org.techtown.diffuser.activity.BaseViewModel
import org.techtown.diffuser.constants.Constants.VIEW_TYPE_COMMON_MORE
import org.techtown.diffuser.model.FailModel
import org.techtown.diffuser.model.Movie
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository,
) : BaseViewModel() {
    private val _trendItems: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val trendItems: LiveData<List<String>> = _trendItems


    fun fetch(title: String) {
        repository
            .getSearch(title)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        val response = result.model

                        val list = response.results.map {
                            Movie(
                                title = it.title,
                                rank = it.releaseDate,
                                imagePoster = it.posterPath,
                                viewType = VIEW_TYPE_COMMON_MORE,
                                overView = it.overview,
                                id = it.id
                            )
                        }.filter {
                            it.imagePoster != null
                        }
                        _items.value = list
                        Log.e("kmh!!!", "Resource.Success : ${_items.value}")
                    }
                    is Resource.Fail -> {
                        Log.e("kmh!!!", "Resource.Fail3 : ${_items.value}")
                        _items.value = listOf(FailModel)
                    }
                }
            }.launchIn(viewModelScope)
    }


    fun fetchTrend() {
        repository
            .getTrend(page)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        val response = result.model

                        val titles = response.results.map {
                            it.title
                        }
                        _trendItems.value = _trendItems.value!! + titles
                    }
                    is Resource.Fail -> {
                    }
                }
            }.launchIn(viewModelScope)
    }


}