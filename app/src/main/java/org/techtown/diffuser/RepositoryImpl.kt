package org.techtown.diffuser

import kotlinx.coroutines.flow.Flow
import org.techtown.diffuser.response.Upcomming
import org.techtown.diffuser.response.detail.cast.CastResult
import org.techtown.diffuser.response.detail.detailmovie.DetailPage_3
import org.techtown.diffuser.response.nowplaying.NowPlayingResponse
import org.techtown.diffuser.response.pupular.PopularMoviesResponse
import org.techtown.diffuser.retrofit.RetrofitService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: RetrofitService
) : BaseRepository(), Repository {
    override fun getPopular(): Flow<Resource<PopularMoviesResponse>> {
       return callApi(
            responseFunction = {
                service.getPopularMovie("ko", 1, "KR")
            }
        )
    }

    override fun getNowPlay(): Flow<Resource<NowPlayingResponse>> = callApi {
        service.getNowPlayingMovie("ko", 1, "KR")
    }

    override fun getDetail(
        movieId: Int
    ): Flow<Resource<DetailPage_3>> = callApi {
        service.getDetailPage(movieId, "ko")   //Datailpage_3 는 BaseResponse를 상속함
    }

    override fun getCast(
        movieId: Int
    ): Flow<Resource<CastResult>> = callApi {
        service.getCast(movieId, "ko")
    }

    override fun getUpComming(): Flow<Resource<Upcomming>> = callApi {
        service.getUpcomming("ko", 1, "KR")
    }

}