package org.techtown.diffuser.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.techtown.diffuser.Repository
import org.techtown.diffuser.Resource
import org.techtown.diffuser.activity.BaseViewModel
import org.techtown.diffuser.fragment.home.HomeAdapter
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.response.trend.ResultTrend
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository,
) : BaseViewModel() {
    private val _trendItems: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val trendItems: LiveData<List<String>> = _trendItems

    init {
        _items.value = listOf()
    }

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
                                viewType = HomeAdapter.VIEW_TYPE_POPULAR_MOVIE,
                                overView = it.overview,
                                id = it.id
                            )
                        }.filter {
                            it.imagePoster != null
                        }
                        _items.value = list
                    }
                    is Resource.Fail -> {
                    }
                }
            }.launchIn(viewModelScope)
    }


    fun fetchTrend() {
        repository
            .getTrend()
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