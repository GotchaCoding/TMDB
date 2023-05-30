package org.techtown.diffuser.fragment.recommend

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.techtown.diffuser.Repository
import org.techtown.diffuser.Resource
import org.techtown.diffuser.activity.BaseViewModel
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.model.TitleModel
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel(){

    init{
        val defaultList = listOf<ItemModel>(
            TitleModel(
                title = "추천영화",
                viewType = Constants.VIEW_TYPE_RECOMMEND_TITLE,
                id = Constants.KEY_RECOMMEND_TITLE_ID,
            )
        )
        _items.value = defaultList
    }

    fun fetch(){
        repository.getTrend().onEach { result ->
            when(result){
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val model = result.model
                    val list = model.results.map {
                        Movie(
                            title = it.title,
                            rank = it.releaseDate,
                            imagePoster = it.posterPath,
                            viewType = Constants.VIEW_TYPE_RECOMMEND_ITEM,
                            id = it.id
                        )
                    }
                    _items.value = _items.value!! + list
                }
                is Resource.Fail -> {
                }
            }

        }.launchIn(viewModelScope)
    }
}