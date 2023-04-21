package org.techtown.diffuser.fragment.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.techtown.diffuser.Repository
import org.techtown.diffuser.Resource
import org.techtown.diffuser.activity.BaseViewModel
import org.techtown.diffuser.model.HorizontalMovieModel
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.model.TitleModel
import org.techtown.diffuser.model.WrappingModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    fun fetch() {
        repository
            .getPopular(page)
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
                                id = it.id
                            )
                        }
                        val horizontalPopularModel =
                            HorizontalMovieModel(
                                list,
                                HomeAdapter.VIEW_TYPE_POPULAR_MOVIE,
                                id = HomeFragment.RECYCLERVIEW_ID_POPULAR
                            )

                        _items.value = _items.value!!.mapIndexed { index, itemModel ->
                            if (index == 1 && itemModel is WrappingModel) {
                                itemModel.copy(
                                    isLoading = false,
                                    model = horizontalPopularModel,
                                    viewType = HomeAdapter.VIEW_TYPE_POPULAR_MOVIE,
                                    isFailure = false
                                )
                            } else {
                                itemModel
                            }
                        }
                    }
                    is Resource.Fail -> {
                        _items.value = _items.value!!.mapIndexed { index, itemModel ->
                            if (index == 1 && itemModel is WrappingModel) {
                                itemModel.copy(
                                    isLoading = false,
                                    model = null,
                                    viewType = HomeAdapter.VIEW_TYPE_POPULAR_MOVIE,
                                    isFailure = true
                                )
                            } else {
                                itemModel
                            }
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun fetch2() {
        repository
            .getNowPlay(page)
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
                                imageDrop = it.backdropPath,
                                viewType = HomeAdapter.VIEW_TYPE_NOW_MOVIE,
                                id = it.id
                            )
                        }
                        val nowPlaying = HorizontalMovieModel(
                            list,
                            HomeAdapter.VIEW_TYPE_NOW_MOVIE,
                            id = HomeFragment.RECYCLERVIEW_ID_NOW
                        )
                        _items.value = items.value!!.mapIndexed { index, itemModel ->
                            if (index == 3 && itemModel is WrappingModel) {
                                itemModel.copy(
                                    isLoading = false,
                                    model = nowPlaying,
                                    viewType = HomeAdapter.VIEW_TYPE_NOW_MOVIE,
                                    isFailure = false,
                                    id = HomeFragment.RECYCLERVIEW_ID_NOW
                                )
                            } else {
                                itemModel
                            }
                        }
                    }
                    is Resource.Fail -> {
                        _items.value = _items.value!!.mapIndexed { index, itemModel ->
                            if (index == 3 && itemModel is WrappingModel) {
                                itemModel.copy(
                                    isLoading = false,
                                    model = null,
                                    viewType = HomeAdapter.VIEW_TYPE_NOW_MOVIE,
                                    isFailure = true,
                                    id = HomeFragment.RECYCLERVIEW_ID_NOW
                                )
                            } else {
                                itemModel
                            }
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun fetchUpcomming() {
        repository
            .getUpComming(page)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        val model = result.model
                        val list = model.results.map {
                            Movie(
                                title = it.title,
                                rank = it.releaseDate,
                                imagePoster = it.posterPath,
                                viewType = HomeAdapter.VIEW_TYPE_UPCOMMING,
                                id = it.id
                            )
                        }
                        val horizontalPopularModel = HorizontalMovieModel(
                            list,
                            HomeAdapter.VIEW_TYPE_UPCOMMING,
                            id = HomeFragment.RECYCLERVIEW_ID_COMMING
                        )

                        _items.value = _items.value!!.mapIndexed { index, itemModel ->
                            if (index == 5 && itemModel is WrappingModel) {
                                itemModel.copy(
                                    isLoading = false,
                                    model = horizontalPopularModel,
                                    viewType = HomeAdapter.VIEW_TYPE_UPCOMMING,
                                    isFailure = false,
                                    id = HomeFragment.RECYCLERVIEW_ID_COMMING
                                )
                            } else {
                                itemModel
                            }
                        }
                    }
                    is Resource.Fail -> {
                        _items.value = _items.value!!.mapIndexed { index, itemModel ->
                            if (index == 5 && itemModel is WrappingModel) {
                                itemModel.copy(
                                    isLoading = false,
                                    model = null,
                                    viewType = HomeAdapter.VIEW_TYPE_UPCOMMING,
                                    isFailure = true,
                                    id = HomeFragment.RECYCLERVIEW_ID_COMMING
                                )
                            } else {
                                itemModel
                            }
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    init {
        val defaultList = listOf(
            TitleModel(
                "인기영화",
                TheMore.THEMORE_POPULAR,
                HomeAdapter.VIEW_TYPE_TITLE,
                HomeFragment.RECYCLERVIEW_ID_TITME
            ),
            WrappingModel(
                true, null,
                HomeAdapter.VIEW_TYPE_POPULAR_MOVIE, id = HomeFragment.RECYCLERVIEW_ID_POPULAR
            ),
            TitleModel(
                "상영중 영화",
                TheMore.THEMORE_NOW,
                HomeAdapter.VIEW_TYPE_TITLE,
                HomeFragment.RECYCLERVIEW_ID_TITME
            ),
            WrappingModel(
                true, null,
                HomeAdapter.VIEW_TYPE_NOW_MOVIE, id = HomeFragment.RECYCLERVIEW_ID_NOW
            ),
            TitleModel(
                "개봉 예정",
                TheMore.THEMORE_COMMING,
                HomeAdapter.VIEW_TYPE_TITLE,
                HomeFragment.RECYCLERVIEW_ID_TITME
            ),
            WrappingModel(
                true, null,
                HomeAdapter.VIEW_TYPE_POPULAR_MOVIE, id = HomeFragment.RECYCLERVIEW_ID_COMMING
            )
        )
        _items.value = defaultList
    }
}