package org.techtown.diffuser.fragment.recommend

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.tasks.await
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
) : BaseViewModel() {
    val database = Firebase.firestore
    var idMovieBookmark: ArrayList<Long> = arrayListOf<Long>()

    init {
        val defaultList = listOf<ItemModel>(
            TitleModel(
                title = "추천영화",
                viewType = Constants.VIEW_TYPE_RECOMMEND_TITLE,
                id = Constants.KEY_RECOMMEND_TITLE_ID,
            )
        )
        _items.value = defaultList
    }


    fun fetch() {
        val job = GlobalScope.launch {
            read()
        }
        runBlocking {
            job.join()
        }

        repository.getTrend(page).onEach { result ->
            when (result) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
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


                    for (i in 0 until idMovieBookmark.size) {
                        list = list.map {
                            if (it.id == idMovieBookmark[i]) {
                                it.copy(isCheckedMark = true)
                            } else {
                                it
                            }
                        }
                    }

                    _items.value = _items.value!! + list
                }
                is Resource.Fail -> {
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
        database.collection("movie").document("${movie.id}").set(movie)
        database.collection("movie").document("${movie.id}")
            .update("isCheckedMark", movie.isCheckedMark.not())
    }


    suspend fun read() {
        database.collection("movie").whereEqualTo("isCheckedMark", true)
            .get()
            .addOnSuccessListener { it ->
                for (i in 0 until it.documents.size) {
                    idMovieBookmark.add(it.documents[i].id.toLong())
                }
            }
            .addOnFailureListener { exception ->
            }.await()
    }
}