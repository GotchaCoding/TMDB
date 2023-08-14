package org.techtown.diffuser.activity.detailpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.techtown.diffuser.Repository
import org.techtown.diffuser.Resource
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.model.CastRv
import org.techtown.diffuser.model.DetailTopModel
import org.techtown.diffuser.model.HorizontalCastModel
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.TitleModel
import org.techtown.diffuser.model.WebData
import org.techtown.diffuser.model.WebModel
import org.techtown.diffuser.model.WrappingDetailModel
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
            title = "",
            overview = "",
            postUrl = "",
            backDropUrl = "",
            id = Constants.KEY_RECYCLERVIEW_ID_TOP_IN_TOP,
            isFailure = false,
            isLoading = false
        )

        val defaultList = listOf(
            WrappingDetailModel(
                isLoading = true,
                castModel = null,
                detailTopModel = topModel,
                viewType = Constants.VIEW_TYPE_DETAIL_BACKGROND,
                isFailure = false,
                id = Constants.KEY_RECYCLERVIEW_ID_TOP,
                webModel = null

            ),
            TitleModel(
                title = "캐스팅",
                theMore = null,
                viewType = Constants.VIEW_TYPE_DETAIL_TITLE,
                id = Constants.KEY_RECYCLERVIEW_ID_TITLE
            ),
            WrappingDetailModel(
                isLoading = true,
                castModel = null,
                detailTopModel = null,
                viewType = Constants.VIEW_TYPE_DETAIL_CASTING,
                isFailure = false,
                id = Constants.KEY_RECYCLERVIEW_ID_CAST,
                webModel = null
            ),
            WrappingDetailModel(
                isLoading = true,
                castModel = null,
                detailTopModel = topModel,
                viewType = Constants.VIEW_TYPE_WEBVIEW,
                isFailure = false,
                id = Constants.KEY_RECYCLERVIEW_ID_WEBVIEW,
                webModel = null
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
                                    isFailure = false,
                                    isLoading = false
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
                                isFailure = false,
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
                                isFailure = true,
                            )
                        } else {
                            itemModel
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun fetchVideo() {
        repository.getVideo(movieId).onEach { result ->
            when (result) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    val model = result.model
                    val list = model.results.map {     // WebModel을 추가했고, WebModel 은 WebData를 가지고잇음. 1개의 영상만 쓸거라 WebData는 List로 저장할 필요는 없음.
                        WebData(                                              // val list는 : List<WebData> 형식으로  결과를 WebData 타입의 리스트로 저장.
                            key = it.key,
                            type = it.type,
                            id = Constants.KEY_RECYCLERVIEW_ID_WEBVIEW,
                            viewType = Constants.VIEW_TYPE_WEBVIEW
                        )
                    }.filter { it.type == "Trailer" }    // 티져영상 말고 트레일러 영상으로 보여주기 위한 필터링.
                        .ifEmpty {                       // 영상이 없는경우 Null 이되는데, 에러를 막기위해  기본 리스트 형식을 1개 만들어줌. 그리고 WebModel의 webData에 넣어주기 위함.
                            listOf(
                                WebData(
                                    key = null,
                                    type = null,
                                    id = Constants.KEY_RECYCLERVIEW_ID_WEBVIEW,
                                    viewType = Constants.VIEW_TYPE_WEBVIEW
                                )
                            )
                        }

                    val webModel = WebModel(
                        webData = list[0],    // webData: WebData 는 List 타입이 아님.   결과값이 많다면 첫번째 리스트만 사용해서  WebData 타입으로 데이터를 넣어줌.
                        id = Constants.KEY_RECYCLERVIEW_ID_WEBVIEW,
                        viewType = Constants.VIEW_TYPE_WEBVIEW
                    )

                    _items.value = items.value!!.mapIndexed { index, itemModel ->
                        if (index == 3 && itemModel is WrappingDetailModel) {
                            itemModel.copy(
                                isLoading = false,
                                castModel = null,
                                detailTopModel = null,
                                webModel = webModel,
                                viewType = Constants.VIEW_TYPE_WEBVIEW,
                                isFailure = false,
                                id = Constants.KEY_RECYCLERVIEW_ID_WEBVIEW

                            )
                        } else {
                            itemModel // index ==0 , index ==1 , index ==2, 는 그대로 .
                        }
                    }
                }

                is Resource.Fail -> {
                    _items.value = items.value!!.mapIndexed { index, itemModel ->
                        if (index == 3 && itemModel is WrappingDetailModel) {
                            itemModel.copy(
                                isLoading = false,
                                castModel = null,
                                detailTopModel = null,
                                webModel = null,
                                viewType = Constants.VIEW_TYPE_WEBVIEW,
                                isFailure = true,
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