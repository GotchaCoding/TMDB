package org.techtown.diffuser.fragment.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.techtown.diffuser.Repository
import org.techtown.diffuser.Resource
import org.techtown.diffuser.activity.BaseViewModel
import org.techtown.diffuser.con.Constants
import org.techtown.diffuser.model.*
import javax.inject.Inject

@HiltViewModel  //뷰모델에서 hilt 주입을 사용하려면 어노테이션.
class HomeViewModel @Inject constructor(
    private val repository: Repository  // 생성자 주입은 RepositoryModule 부분에서 Repository  받아옴.
) : BaseViewModel() {

    init {
        val defaultList =
            listOf(
                //fetch 하여 데이터를 넣기 전에  뷰를 미리 구성하여 세팅.  fetch 전 로딩뷰를 보여주기 위함. 로딩뷰 default 로 보여주고 있다가 Fetch 이후 사라지게 .
                TitleModel(
                    "인기영화",
                    TheMore.THEMORE_POPULAR,
                    HomeAdapter.VIEW_TYPE_TITLE,  //뷰타입 뷰홀더 생성과 온바인드시에 활용.
                    Constants.KEY_RECYCLERVIEW_ID_TITLE   //diffUtil 에서 Item 비교시 활용. 이후 Movie id를 다시 넣어주어 고유값을 가지게 될 예정.
                ),
                WrappingModel( // 로딩뷰와  실패뷰, 그리고 호리즌탈무비모델을 생성자속성으로 가지고 있음.
                    true, null,   // 로딩 구현.  모델은 null
                    HomeAdapter.VIEW_TYPE_POPULAR_MOVIE, id = Constants.KEY_RECYCLERVIEW_ID_POPULAR
                ),
                TitleModel(
                    "상영중 영화",
                    TheMore.THEMORE_NOW,
                    HomeAdapter.VIEW_TYPE_TITLE,
                    Constants.KEY_RECYCLERVIEW_ID_TITLE
                ),
                WrappingModel(
                    true, null,
                    HomeAdapter.VIEW_TYPE_NOW_MOVIE, id = Constants.KEY_RECYCLERVIEW_ID_NOW
                ),
                TitleModel(
                    "개봉 예정",
                    TheMore.THEMORE_COMMING,
                    HomeAdapter.VIEW_TYPE_TITLE,
                    Constants.KEY_RECYCLERVIEW_ID_TITLE
                ),
                WrappingModel(
                    true,
                    null,
                    HomeAdapter.VIEW_TYPE_UPCOMMING,
                    id = Constants.KEY_RECYCLERVIEW_ID_COMMING  // 지금단계에서는 VIEW_TYPE_UPCOMMING 을 쓰나 VIEW_TYPE_NOW_MOVIE 를 쓰나 크게 상관 없음. 어짜피 같은 레이아웃 사용.
                ),
            )
        _items.value =
            defaultList  //MutableLiveData<List<ItemModel>> 타입의 items를  로딩뷰가 보이게 디폴트 리스트로 설정.
    }


    fun fetch() {

        val page = 1
        repository
            .getPopular(page) //이벤트가 발생하고 있는 부분.  // repository는 RopositoryModule 에서 repositoryImpl를 주입받   // 실제로는 impl 인데 인터페이스 제약 걸려잇음.
            .onEach { result ->  // 위의 이벤트 발생부분에 대한 처리부분.  이벤트 발생결과는 callApi 의 결과이므로 Resource seal 클래스타입으로 리턴받음.
                when (result) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        val response =
                            result.model  //Resource<out T> 이기 때문에  model = BaseResponse가 됨  이경우 PopularMoviesResponse .
                        val list =
                            response.results.map { //response의 results 리스트 결과값을 map으로  필요한 정보만 뽑아서 Movie 클래스에 저장 --> List<Moive> 가 됨.
                                Movie(
                                    title = it.title,
                                    rank = it.releaseDate,
                                    imagePoster = it.posterPath,
                                    viewType = HomeAdapter.VIEW_TYPE_POPULAR_MOVIE,
                                    id = it.id
                                )
                            }
                        val horizontalPopularModel =
                            HorizontalMovieModel(
                                list, //생성자 속성으로 List<Moive> 를 가지고 있음
                                HomeAdapter.VIEW_TYPE_POPULAR_MOVIE,
                                id = Constants.KEY_RECYCLERVIEW_ID_POPULAR
                            )

                        _items.value =
                            _items.value!!.mapIndexed { index, itemModel -> // 라이브 데이터 업데이트.   이경우 기존 defaultList 의  index 1 에다가 map 을통해 값을 변경해서 넣고 hash코드 변경
                                if (index == 1 && itemModel is WrappingModel) { // 인덱스가 1이고, WrappingModel 타입이면
                                    itemModel.copy(  //데이터 클래스를 COPY 하여 깊은 복사(데이터까지 복사) 하여 새로운 인스턴스를 만드록 새로운 hashcode를 가지게하여 diffUtill 때  컨텐츠 비교를 가능하게 함.
                                        isLoading = false,  //로딩뷰 제거
                                        model = horizontalPopularModel,  // 디폴트는 HorizontalMovieModel null 이엇지만 List<Moive>를 넣은 HorizontalMovieModel를 넣어줌.
                                        viewType = HomeAdapter.VIEW_TYPE_POPULAR_MOVIE,
                                        isFailure = false
                                    )
                                } else {   // 이거는 map으로 값을 변경 안하고 받아온 itemModel 을 그대로 리턴.( defaultList)
                                    itemModel
                                }
                            }
                    }
                    is Resource.Fail -> { // 레트로핏 통신 실패시  Resource.Fail 을 응답받게 됨( http or io exception)
                        Log.e("kmh!!!", "Resource.Fail1 : ${_items.value}")
                        _items.value =
                            _items.value!!.mapIndexed { index, itemModel ->  //실패시  레트로핏 통신 받아서 movie List 식으로 넣어줘야하는 부분이 없으므로
                                if (index == 1 && itemModel is WrappingModel) {  //인덱스 1 부분의 로딩부분을 제거하고 model은 디폴트와 마찬가지로 그대로 null, 그리고 실패뷰를 true로 설정.
                                    Log.e("kmh!!!", "Resource.Fail2 : ${_items.value}")
                                    itemModel.copy(
                                        isLoading = false,
                                        model = null,
                                        viewType = HomeAdapter.VIEW_TYPE_POPULAR_MOVIE,
                                        isFailure = true
                                    )
                                } else {  // 이거는 map으로 값을 변경 안하고 받아온 itemModel 을 그대로 리턴.( defaultList)
                                    itemModel
                                }
                            }
                    }
                }
            }
            .launchIn(viewModelScope)   // viewModelScope는 finish 될때까지 코드를 실행함. (화면뒤로가기시 석세스 페일 안불림) (getPopular 이후 뒤로가기 누르면 뷰모델 스코프 종료) collect는 코드진행이 멈추므로(스트림 끝날때까지 기다림) launchin을 사용(새로운 코루틴 만들고 이벤트 동작시마다 동작).
    }

    fun fetchNowPlay() {
        repository
            .getNowPlay(page)
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
                                imageDrop = it.backdropPath,
                                viewType = HomeAdapter.VIEW_TYPE_NOW_MOVIE,
                                id = it.id
                            )
                        }
                        val nowPlaying = HorizontalMovieModel(
                            list,
                            HomeAdapter.VIEW_TYPE_NOW_MOVIE,
                            id = Constants.KEY_RECYCLERVIEW_ID_NOW
                        )
                        _items.value = items.value!!.mapIndexed { index, itemModel ->
                            if (index == 3 && itemModel is WrappingModel) {
                                itemModel.copy(
                                    isLoading = false,
                                    model = nowPlaying,
                                    viewType = HomeAdapter.VIEW_TYPE_NOW_MOVIE,
                                    isFailure = false,
                                    id = Constants.KEY_RECYCLERVIEW_ID_NOW
                                )
                            } else {
                                itemModel
                            }
                        }
                    }
                    is Resource.Fail -> {
                        _items.value = _items.value!!.mapIndexed { index, itemModel ->
                            if (index == 3 && itemModel is WrappingModel) {
                                itemModel.copy(
                                    isLoading = false,
                                    model = null,
                                    viewType = HomeAdapter.VIEW_TYPE_NOW_MOVIE,
                                    isFailure = true,
                                    id = Constants.KEY_RECYCLERVIEW_ID_NOW
                                )
                            } else {
                                itemModel
                            }
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun fetchUpComming() {
        repository
            .getUpComming(page)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        val model = result.model
                        val list = model.results.map {
                            Movie(
                                title = it.title,
                                rank = it.releaseDate,
                                imagePoster = it.posterPath,
                                viewType = HomeAdapter.VIEW_TYPE_UPCOMMING,
                                id = it.id
                            )
                        }
                        val horizontalPopularModel = HorizontalMovieModel(
                            list,
                            HomeAdapter.VIEW_TYPE_UPCOMMING,
                            id = Constants.KEY_RECYCLERVIEW_ID_COMMING
                        )

                        _items.value = _items.value!!.mapIndexed { index, itemModel ->
                            if (index == 5 && itemModel is WrappingModel) {
                                itemModel.copy(
                                    isLoading = false,
                                    model = horizontalPopularModel,
                                    viewType = HomeAdapter.VIEW_TYPE_UPCOMMING,
                                    isFailure = false,
                                    id = Constants.KEY_RECYCLERVIEW_ID_COMMING
                                )
                            } else {
                                itemModel
                            }
                        }
                    }
                    is Resource.Fail -> {
                        _items.value = _items.value!!.mapIndexed { index, itemModel ->
                            if (index == 5 && itemModel is WrappingModel) {
                                itemModel.copy(
                                    isLoading = false,
                                    model = null,
                                    viewType = HomeAdapter.VIEW_TYPE_UPCOMMING,
                                    isFailure = true,
                                    id = Constants.KEY_RECYCLERVIEW_ID_COMMING
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