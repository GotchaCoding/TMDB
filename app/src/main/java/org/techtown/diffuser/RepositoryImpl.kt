package org.techtown.diffuser

import kotlinx.coroutines.flow.Flow
import org.techtown.diffuser.response.Upcomming
import org.techtown.diffuser.response.detail.cast.CastResult
import org.techtown.diffuser.response.detail.detailmovie.DetailPage_3
import org.techtown.diffuser.response.nowplaying.NowPlayingResponse
import org.techtown.diffuser.response.pupular.PopularMoviesResponse
import org.techtown.diffuser.response.search.SearchResponse
import org.techtown.diffuser.retrofit.RetrofitService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: RetrofitService
) : BaseRepository(), Repository {
    override fun getPopular(page : Int): Flow<Resource<PopularMoviesResponse>> {
       return callApi(
            responseFunction = {
                service.getPopularMovie("ko", page, "KR")
            }
        )
    }

    override fun getNowPlay(page : Int): Flow<Resource<NowPlayingResponse>> = callApi {
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

    override fun getUpComming(page : Int): Flow<Resource<Upcomming>> = callApi {
        service.getUpcomming("ko", page, "KR")
    }

    override fun getSearch(title: String): Flow<Resource<SearchResponse>> {
        return callApi(
            responseFunction = {
                service.getSearch("ko", 1, title)
            }
        )
    }
}