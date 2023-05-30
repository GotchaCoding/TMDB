package org.techtown.diffuser.activity.detailpage

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.techtown.diffuser.Repository
import org.techtown.diffuser.Resource
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.model.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(

    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {
    val movieId = savedStateHandle.get<Long>("movie_id") ?: 0

    private val _items: MutableLiveData<List<ItemModel>> = MutableLiveData()
    val items: LiveData<List<ItemModel>> = _items

    init {
        val topModel = DetailTopModel(
            "",
            "",
            "",
            "",
            id = Constants.KEY_RECYCLERVIEW_ID_TOP_IN_TOP
        )

        val defaultList = listOf(
            WrappingDetailModel(
                true,
                null,
                topModel,
                Constants.VIEW_TYPE_DETAIL_BACKGROND,
                isFailure = false,
                id = Constants.KEY_RECYCLERVIEW_ID_TOP
            ),
            TitleModel(
                "캐스팅",
                null,
                Constants.VIEW_TYPE_DETAIL_TITLE,
                Constants.KEY_RECYCLERVIEW_ID_TITLE
            ),
            WrappingDetailModel(
                true,
                null,
                null,
                Constants.VIEW_TYPE_DETAIL_CASTING,
                false,
                Constants.KEY_RECYCLERVIEW_ID_CAST
            )
        )
        _items.value = defaultList

    }

    fun fetch() {
        repository.getDetail(
            movieId
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val model = result.model
                    _items.value = items.value!!.mapIndexed { index, itemModel ->
                        if (index == 0 && itemModel is WrappingDetailModel) {
                            itemModel.copy(
                                isLoading = false,
                                castModel = null,
                                detailTopModel = DetailTopModel(
                                    title = model.title,
                                    overview = model.overview,
                                    postUrl = model.posterPath,
                                    backDropUrl = model.backdropPath,
                                    id = Constants.KEY_RECYCLERVIEW_ID_TOP_IN_TOP,
                                    isFailure = false
                                ),
                                viewType = Constants.VIEW_TYPE_DETAIL_BACKGROND,
                                isFailure = false,
                                id = Constants.KEY_RECYCLERVIEW_ID_TOP
                            )

                        } else {
                            itemModel
                        }
                    }
                }
                is Resource.Fail -> {
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
                                    isFailure = true,
                                    id = Constants.KEY_RECYCLERVIEW_ID_TOP_IN_TOP,
                                    isLoading = false,
                                ),
                                viewType = Constants.VIEW_TYPE_DETAIL_BACKGROND,
                                isFailure = true,
                                id = Constants.KEY_RECYCLERVIEW_ID_TOP

                            )
                        } else {
                            itemModel
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun fetchCast() {
        repository.getCast(
            movieId
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val model = result.model
                    val list = model.cast.map {
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
                            id = Constants.KEY_RECYCLERVIEW_ID_CAST,
                            list,
                            Constants.VIEW_TYPE_DETAIL_CASTING
                        )
                    _items.value = items.value!!.mapIndexed { index, itemModel ->
                        if (index == 2 && itemModel is WrappingDetailModel) {
                            itemModel.copy(
                                isLoading = false,
                                castModel = castModel,
                                detailTopModel = null,
                                viewType = Constants.VIEW_TYPE_DETAIL_CASTING,
                                isFailure = false
                            )
                        } else {
                            itemModel
                        }
                    }
                }
                is Resource.Fail -> {
                    _items.value = items.value!!.mapIndexed { index, itemModel ->
                        if (index == 2 && itemModel is WrappingDetailModel) {
                            itemModel.copy(
                                isLoading = false,
                                castModel = null,
                                detailTopModel = null,
                                viewType = Constants.VIEW_TYPE_DETAIL_CASTING,
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

}