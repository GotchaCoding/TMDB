package org.techtown.diffuser.fragment.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.techtown.diffuser.Repository
import org.techtown.diffuser.RepositoryRoom
import org.techtown.diffuser.Resource
import org.techtown.diffuser.SingleLiveEvent
import org.techtown.diffuser.activity.BaseViewModel
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.constants.Constants.VIEW_TYPE_COMMON_MORE
import org.techtown.diffuser.model.EmptyModel
import org.techtown.diffuser.model.FailModel
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.room.Word
import org.techtown.diffuser.room.WordDaoModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository,
    private val repositoryRoom: RepositoryRoom
) : BaseViewModel() {
    private val _toast: MutableLiveData<String> = MutableLiveData()
    val toast: LiveData<String> = _toast

    val recentWords: LiveData<List<Word>> = repositoryRoom.recentWords.switchMap {
        MutableLiveData(
            it.map {
                Word.of(it)
            }
        )
    }

    val clearKeywordEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

    private var searchJob: Job? = null
    private val searchDelayMillis: Long = 1000

    fun fetch(title: String) {
        repository
            .getSearch(title)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        Log.e("kmh", "allWords 값 확인 : ${recentWords.value.toString()}")
                    }

                    is Resource.Success -> {
                        val response = result.model

                        val list = response.results.map {
                            Movie(
                                title = it.title,
                                rank = it.releaseDate,
                                imagePoster = it.posterPath,
                                viewType = VIEW_TYPE_COMMON_MORE,
                                overView = it.overview,
                                id = it.id
                            )
                        }.filter {
                            it.imagePoster != null
                        }
                        _items.value = list

                        if (list.isEmpty()) {
                            _toast.value = "검색 결과가 없습니다."
                            _items.value = listOf(EmptyModel)
                        }
                        Log.e("kmh!!!", "Resource.Success : ${_items.value}")
                    }

                    is Resource.Fail -> {
                        Log.e("kmh!!!", "Resource.Fail3 : ${_items.value}")
                        _items.value = listOf(FailModel)
                    }
                }
            }.launchIn(viewModelScope)
    }


    fun insertWord(keyWord: String) {
        if(keyWord.isEmpty()) {
            return
        }
        viewModelScope.launch {
            //최근 검색어 목록을 가져옴.    recentWords 객체의 값을 가변리스트로 변환
            val recentList = recentWords.value.orEmpty().toMutableList()

            val word = Word(
                word = keyWord,
                viewType = Constants.VIEW_TYPE_WORD_RECORD,
                id = Constants.KEY_RECYCLERVIEW_ID_WORD_RECORD
            )

            //새로운 검색어를 추가
            recentList.add(0, word)

            //최대 5개의 검색어만 유지
            if (recentList.size > 5) {
                val oldestWord = recentList.removeAt(recentList.size - 1)
                repositoryRoom.deleteWord(WordDaoModel.of(oldestWord))
            }
            repositoryRoom.insert(WordDaoModel.wordForInsert(keyWord))
        }
    }

    fun deleteSelectedWord(word: Word) {
        viewModelScope.launch {
            repositoryRoom.deleteWord(word = WordDaoModel.of(word))
        }
    }

    fun onSearch(keyWord: String) {
        if (keyWord.isEmpty()) {
            return
        }

        searchJob?.cancel() // 기존 검색작업 취소: 널이 아니면 실행(작업중이면 캔슬)
        searchJob = viewModelScope.launch {
            delay(searchDelayMillis)
            fetch(keyWord)
        }
    }

    fun clearEdtAndFocus(){
        clearKeywordEvent.call()
    }

}