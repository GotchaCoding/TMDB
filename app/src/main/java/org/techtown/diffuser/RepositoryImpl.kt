package org.techtown.diffuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import org.techtown.diffuser.response.detail.cast.CastResult
import org.techtown.diffuser.response.detail.detailmovie.DetailPage_3
import org.techtown.diffuser.response.nowplaying.NowPlayingResponse
import org.techtown.diffuser.response.pupular.PopularMoviesResponse
import org.techtown.diffuser.response.search.SearchResponse
import org.techtown.diffuser.response.trend.TrendResponse
import org.techtown.diffuser.response.upcomming.Upcomming
import org.techtown.diffuser.response.video.VideoResponse
import org.techtown.diffuser.retrofit.RetrofitService
import org.techtown.diffuser.room.Word
import org.techtown.diffuser.room.WordDao
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: RetrofitService,
    private val wordDao: WordDao
) : BaseRepository(), Repository,
    RepositoryRoom { //Repository 메소드 오버라이드 하고  BaseRepository 클래스 메서드 사용.

    override val allWord: LiveData<List<Word>> = wordDao.getRecodedWord().asLiveData()

    override suspend fun insert(word: Word) = wordDao.insert(word)


    override fun getPopular(page: Int): Flow<Resource<PopularMoviesResponse>> {
        return callApi(   //callApi 메소드의 매개변수는 suspend 람다함수이고 리턴타입이 BaseResponse 타입임.
            responseFunction = {  //람다함수를 매개변수로 가짐
                service.getPopularMovie(
                    "ko",
                    page,
                    "KR"
                )   //getPopularMovie suspend 함수의 리턴 타입은  BaseResponse
            }
        )
    }

    override fun getNowPlay(page: Int): Flow<Resource<NowPlayingResponse>> = callApi {
        service.getNowPlayingMovie("ko", page, "KR")
    }

    override fun getDetail(
        movieId: Long
    ): Flow<Resource<DetailPage_3>> = callApi {
        service.getDetailPage(movieId, "ko")   //Datailpage_3 는 BaseResponse를 상속함
    }

    override fun getCast(
        movieId: Long
    ): Flow<Resource<CastResult>> = callApi {
        service.getCast(movieId, "ko")
    }

    override fun getUpComming(page: Int): Flow<Resource<Upcomming>> = callApi {
        service.getUpcomming("ko", page, "KR")
    }

    override fun getSearch(title: String): Flow<Resource<SearchResponse>> {
        return callApi(
            responseFunction = {
                service.getSearch("ko", 1, title)
            }
        )
    }

    override fun getTrend(page: Int): Flow<Resource<TrendResponse>> {
        return callApi(
            responseFunction = {
                service.getTrend("ko", page)
            }
        )
    }

    override fun getVideo(movieId: Long): Flow<Resource<VideoResponse>> = callApi {
        service.getVideo(movieId, "ko")
    }


}