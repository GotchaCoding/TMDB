package org.techtown.diffuser.fragment.recommend

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.tasks.await
import org.techtown.diffuser.FirestoreRepository
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
    private val repository: Repository,
    fireRepository: FirestoreRepository
) : BaseViewModel() {
    private val database = fireRepository.getFireStore()
    private var isLoading = false
    private val defaultList: List<ItemModel> = listOf(
        TitleModel(
            title = "추천영화",
            viewType = Constants.VIEW_TYPE_RECOMMEND_TITLE,
            id = Constants.KEY_RECOMMEND_TITLE_ID,
        )
    )

    init {
        _items.value = defaultList
    }

    fun onRefresh() {
        fetch()
    }

    fun fetch() {
        if (isLoading) {
            return
        }

        repository.getTrend(page).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    isLoading = true
                }

                is Resource.Success -> {
                    isLoading = false
                    val model = result.model
                    var list = model.results.map {
                        Movie(
                            title = it.title,
                            rank = it.releaseDate,
                            imagePoster = it.posterPath,
                            viewType = Constants.VIEW_TYPE_RECOMMEND_ITEM,
                            id = it.id
                        )
                    }

                    val movieBookmarkIds = getMovieBookmarkIds()

                    for (movieBookmarkId in movieBookmarkIds) {
                        list = list.map {
                            if (it.id == movieBookmarkId) {
                                it.copy(isCheckedMark = true)
                            } else {
                                it
                            }
                        }
                    }

                    _items.value = defaultList + list
                }

                is Resource.Fail -> {
                    isLoading = false
                }
            }

        }.launchIn(viewModelScope)
    }


    fun onFavorite(movie: Movie?) {
        movie ?: return

        _items.value = _items.value!!.map {
            if (movie == it) {
                movie.copy(
                    isCheckedMark = movie.isCheckedMark.not()
                )
            } else {
                it
            }
        }
        database.collection("movie").document("${movie.id}").set(
            movie.copy(
                isCheckedMark = movie.isCheckedMark.not()
            )
        )
    }

    private suspend fun getMovieBookmarkIds(): List<Long> =
        database.collection("movie").whereEqualTo("checkedMark", true)
            .get()
            .await().documents.map {
                it.id.toLong()
            }

}