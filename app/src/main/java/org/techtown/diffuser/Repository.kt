package org.techtown.diffuser

import kotlinx.coroutines.flow.Flow
import org.techtown.diffuser.response.Upcomming
import org.techtown.diffuser.response.detail.cast.CastResult
import org.techtown.diffuser.response.detail.detailmovie.DetailPage_3
import org.techtown.diffuser.response.nowplaying.NowPlayingResponse
import org.techtown.diffuser.response.pupular.PopularMoviesResponse

interface Repository {
    fun getPopular(): Flow<Resource<PopularMoviesResponse>>
    fun getNowPlay(): Flow<Resource<NowPlayingResponse>>
    fun getDetail(movieId : Int): Flow<Resource<DetailPage_3>>
    fun getCast(movieId : Int): Flow<Resource<CastResult>>
    fun getUpComming(): Flow<Resource<Upcomming>>
}