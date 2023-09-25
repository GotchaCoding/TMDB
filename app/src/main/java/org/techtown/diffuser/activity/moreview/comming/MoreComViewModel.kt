package org.techtown.diffuser.activity.moreview.comming

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
class MoreComViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository
) : BaseViewModel() {

    fun fetch() {
        if (isLoading()) return   //스크롤 리스너 여러번 fetch 되는거 막기위해.

        serviceRepository
            .getUpComming(page)
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
                                viewType = Constants.VIEW_TYPE_UPCOMMING,
                                id = it.id
                            )
                        }.filter {
                            it.imagePoster != null
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