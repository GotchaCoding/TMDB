package org.techtown.diffuser.fragment.search

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.techtown.diffuser.Repository
import org.techtown.diffuser.Resource
import org.techtown.diffuser.activity.BaseViewModel
import org.techtown.diffuser.fragment.home.HomeAdapter
import org.techtown.diffuser.model.Movie
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository,

    ) : BaseViewModel() {

    init {
        _items.value = listOf()
    }

    fun fetch() {
        repository
            .getSearch(editText)
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

                        _items.value = list
                    }
                    is Resource.Fail -> {

                    }
                }
            }.launchIn(viewModelScope)
    }
}