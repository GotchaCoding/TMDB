package org.techtown.diffuser

import kotlinx.coroutines.flow.Flow
import org.techtown.diffuser.response.Upcomming
import org.techtown.diffuser.response.detail.cast.CastResult
import org.techtown.diffuser.response.detail.detailmovie.DetailPage_3
import org.techtown.diffuser.response.nowplaying.NowPlayingResponse
import org.techtown.diffuser.response.pupular.PopularMoviesResponse

interface Repository {
    fun getPopular(page : Int): Flow<Resource<PopularMoviesResponse>>
    fun getNowPlay(page : Int): Flow<Resource<NowPlayingResponse>>
    fun getDetail(movieId : Long): Flow<Resource<DetailPage_3>>
    fun getCast(movieId : Long): Flow<Resource<CastResult>>
    fun getUpComming(page : Int): Flow<Resource<Upcomming>>
}