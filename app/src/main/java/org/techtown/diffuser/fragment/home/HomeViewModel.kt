package org.techtown.diffuser.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.techtown.diffuser.model.*
import org.techtown.diffuser.response.pupular.PopularMoviesResponse
import org.techtown.diffuser.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val service: RetrofitInterface
) : ViewModel() {
    val _items: MutableLiveData<List<ItemModel>> = MutableLiveData()
    val items: LiveData<List<ItemModel>> = _items

    fun fetch() {
        service.getPopularMovie(
            "ko",
            1
        ).enqueue(object : Callback<PopularMoviesResponse> {
            override fun onResponse(
                call: Call<PopularMoviesResponse>,
                response: Response<PopularMoviesResponse>
            ) {
                val result = response.body()

                val list = result!!.results.map {
                    Movie(
                        title = it.title,
                        rank = it.releaseDate,
                        imagePoster = it.posterPath,
                        id = it.id
                    )
                }
                val horizontalPopularModel =
                    HorizontalMovieModel(
                        list,
                        HomeAdapter.VIEW_TYPE_POPULAR_MOVIE,
                        id = HomeFragment.RECYCLERVIEW_ID_POPULAR
                    )

                _items.value = items.value!!.mapIndexed { index, itemModel ->
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

            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                Log.d("kmh", t.toString())
                _items.value = items.value!!.mapIndexed { index, itemModel ->
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

        })
    }

    init {
        val defaultList = listOf(
            Title("인기영화", HomeAdapter.VIEW_TYPE_TITLE, HomeFragment.RECYCLERVIEW_ID_TITME),
            WrappingModel(true, null,
                HomeAdapter.VIEW_TYPE_POPULAR_MOVIE, id = HomeFragment.RECYCLERVIEW_ID_POPULAR
            ),
            Title("상영중 영화", HomeAdapter.VIEW_TYPE_TITLE, HomeFragment.RECYCLERVIEW_ID_TITME),
            WrappingModel(true, null,
                HomeAdapter.VIEW_TYPE_NOW_MOVIE, id = HomeFragment.RECYCLERVIEW_ID_NOW
            ),
            Title("개봉 예정", HomeAdapter.VIEW_TYPE_TITLE, HomeFragment.RECYCLERVIEW_ID_TITME),
            WrappingModel(true, null,
                HomeAdapter.VIEW_TYPE_POPULAR_MOVIE, id = HomeFragment.RECYCLERVIEW_ID_COMMING
            )
        )
        _items.value = defaultList
    }

}