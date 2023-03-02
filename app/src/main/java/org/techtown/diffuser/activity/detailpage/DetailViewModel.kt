package org.techtown.diffuser.activity.detailpage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.techtown.diffuser.model.*
import org.techtown.diffuser.response.detail.cast.CastResult
import org.techtown.diffuser.response.detail.detailmovie.DetailPage_3
import org.techtown.diffuser.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val service: RetrofitInterface
) : ViewModel() {
    val movieId = savedStateHandle.get<Int>("movie_id") ?: 0

    private val _items: MutableLiveData<List<ItemModel>> = MutableLiveData()
    val items: LiveData<List<ItemModel>> = _items


    companion object {
        const val RECYCLERVIEW_ID_TOP_IN_TOP = -1L
        const val RECYCLERVIEW_ID_TOP = -2L
        const val RECYCLERVIEW_ID_TITLE = -3L
        const val RECYCLERVIEW_ID_CAST = -4L
    }


    init {

        val topModel = DetailTopModel(
            "",
            "",
            "",
            "",
            id = RECYCLERVIEW_ID_TOP_IN_TOP
        )

        val defaultList = listOf(
            WrappingDetailModel(
                true,
                null,
                topModel,
                DetailAdapter.VIEW_TYPE_DETAIL_BACKGROND,
                isFailure = false,
                id = RECYCLERVIEW_ID_TOP
            ),
            Title(
                "캐스팅",
                DetailAdapter.VIEW_TYPE_DETAIL_TITLE,
                RECYCLERVIEW_ID_TITLE
            ),
            WrappingDetailModel(
                true,
                null,
                null,
                DetailAdapter.VIEW_TYPE_DETAIL_CASTING,
                false,
                RECYCLERVIEW_ID_CAST
            )
        )
        _items.value = defaultList

    }

     fun fetch() {
        service.getDetailPage(
            movieId,
            "ko"
        ).enqueue(object : Callback<DetailPage_3> {
            override fun onResponse(call: Call<DetailPage_3>, response: Response<DetailPage_3>) {
                val result = response.body()
                if (result != null) {
                    _items.value = items.value!!.mapIndexed { index, itemModel ->
                        if (index == 0 && itemModel is WrappingDetailModel) {
                            Log.d("kmh2", "fetch on response")
                            itemModel.copy(
                                isLoading = false,
                                castModel = null,
                                detailTopModel = DetailTopModel(
                                    title = result.title,
                                    overview = result.overview,
                                    postUrl = result.posterPath,
                                    backDropUrl = result.backdropPath,
                                    id = RECYCLERVIEW_ID_TOP_IN_TOP,
                                    isfailure = false
                                ),
                                viewType = DetailAdapter.VIEW_TYPE_DETAIL_BACKGROND,
                                isFailure = false,
                                id = RECYCLERVIEW_ID_TOP
                            )

                        } else {
                            itemModel
                        }
                    }
                }
            }

            override fun onFailure(call: Call<DetailPage_3>, t: Throwable) {
                Log.d("kmh", t.toString())
                _items.value = items.value!!.mapIndexed { index, itemModel ->
                    if (index == 0 && itemModel is WrappingDetailModel) {
                        itemModel.copy(
                            isLoading = false,
                            castModel = null,
                            detailTopModel = DetailTopModel(
                                title = null,
                                overview = "",
                                postUrl = "",
                                backDropUrl = "",
                                isfailure = true,
                                id = RECYCLERVIEW_ID_TOP_IN_TOP,
                                isLoading = false,
                            ),
                            viewType = DetailAdapter.VIEW_TYPE_DETAIL_BACKGROND,
                            isFailure = true,
                            id = RECYCLERVIEW_ID_TOP

                        )
                    } else {
                        itemModel
                    }
                }
            }
        })
    }

     fun fetchCast() {
        service.getCast(
            movieId,
            "ko"
        ).enqueue(object : Callback<CastResult> {
            override fun onResponse(call: Call<CastResult>, response: Response<CastResult>) {
                val result = response.body()
                if (result != null) {
                    val list = result!!.cast.map {
                        CastRv(
                            imgActor = it.profilePath,
                            castChracter = it.character,
                            castName = it.name
                        )
                    }.filter {
                        it.imgActor != null
                    }

                    val castModel =
                        HorizontalCastModel(
                            id = RECYCLERVIEW_ID_CAST,
                            list,
                            DetailAdapter.VIEW_TYPE_DETAIL_CASTING
                        )
                    _items.value = items.value!!.mapIndexed { index, itemModel ->
                        if (index == 2 && itemModel is WrappingDetailModel) {
                            itemModel.copy(
                                isLoading = false,
                                castModel = castModel,
                                detailTopModel = null,
                                viewType = DetailAdapter.VIEW_TYPE_DETAIL_CASTING,
                                isFailure = false
                            )
                        } else {
                            itemModel
                        }
                    }

                }
            }

            override fun onFailure(call: Call<CastResult>, t: Throwable) {
                Log.d("kmh", t.toString())
                _items.value = items.value!!.mapIndexed { index, itemModel ->
                    if (index == 2 && itemModel is WrappingDetailModel) {
                        itemModel.copy(
                            isLoading = false,
                            castModel = null,
                            detailTopModel = null,
                            viewType = DetailAdapter.VIEW_TYPE_DETAIL_CASTING,
                            isFailure = true
                        )
                    } else {
                        itemModel
                    }
                }
            }

        })
    }
}