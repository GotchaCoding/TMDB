package org.techtown.diffuser

import kotlinx.coroutines.flow.Flow
import org.techtown.diffuser.response.detail.cast.CastResult
import org.techtown.diffuser.response.detail.detailmovie.DetailPage_3
import org.techtown.diffuser.response.nowplaying.NowPlayingResponse
import org.techtown.diffuser.response.pupular.PopularMoviesResponse
import org.techtown.diffuser.response.search.SearchResponse
import org.techtown.diffuser.response.trend.TrendResponse
import org.techtown.diffuser.response.upcomming.Upcomming

interface Repository {
    fun getPopular(page: Int): Flow<Resource<PopularMoviesResponse>>  // 리턴타입으로 sealedClass Resource.Loading<BaseResoponse>  , Resouce.Success<BaseResponse> 등의 리턴을 받
    fun getNowPlay(page: Int): Flow<Resource<NowPlayingResponse>>
    fun getDetail(movieId: Long): Flow<Resource<DetailPage_3>>
    fun getCast(movieId: Long): Flow<Resource<CastResult>>
    fun getUpComming(page: Int): Flow<Resource<Upcomming>>
    fun getSearch(title: String): Flow<Resource<SearchResponse>>
    fun getTrend(page: Int): Flow<Resource<TrendResponse>>

}