package org.techtown.diffuser.fragment.recommend.bottomsheet

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.techtown.diffuser.Repository
import org.techtown.diffuser.Resource
import org.techtown.diffuser.activity.BaseViewModel
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.model.BottomLoadingModel
import org.techtown.diffuser.model.Movie
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    fun fetch() {
        if (isLoading()) return

        repository.getTrend(page).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _items.value = pureItems() + BottomLoadingModel
                }
                is Resource.Success -> {
                    val model = result.model
                    var list = model.results.mapIndexed { index, it ->

                        val num = index % 20

                        if (num == 1 || num == 9 || num == 12) {
                            Movie(
                                title = it.title,
                                rank = it.releaseDate,
                                imagePoster = it.posterPath,
                                viewType = Constants.VIEW_TYPE_BOTTOM_BIGPIC,
                                imageDrop = it.backdropPath,
                                id = it.id,
                                overView = it.overview
                            )
                        } else if (num == 19) {
                            Movie(
                                title = it.title,
                                rank = it.releaseDate,
                                imagePoster = it.posterPath,
                                viewType = Constants.VIEW_TYPE_BOTTOM_BIGPIC,
                                imageDrop = null,
                                id = it.id,
                                overView = it.overview
                            )
                        } else {
                            Movie(
                                title = it.title,
                                rank = it.releaseDate,
                                imagePoster = it.posterPath,
                                viewType = Constants.VIEW_TYPE_BOTTOMSHEET,
                                imageDrop = it.backdropPath,
                                id = it.id,
                                overView = it.overview
                            )
                        }
                    }.filter {
                        it.imageDrop != null
                    }

                    page++
                    if (page > 4) {
                        list = list.map {
                            it
                            it.copy(
                                viewType = Constants.VIEW_TYPE_BOTTOMSHEET,
                            )
                        }
                    }
                    _items.value = pureItems() + list

                }
                is Resource.Fail -> {
                }
            }

        }.launchIn(viewModelScope)
    }

}