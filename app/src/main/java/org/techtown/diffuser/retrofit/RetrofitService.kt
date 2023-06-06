package org.techtown.diffuser.retrofit

import org.techtown.diffuser.response.upcomming.Upcomming
import org.techtown.diffuser.response.detail.cast.CastResult
import org.techtown.diffuser.response.detail.detailmovie.DetailPage_3
import org.techtown.diffuser.response.nowplaying.NowPlayingResponse
import org.techtown.diffuser.response.pupular.PopularMoviesResponse
import org.techtown.diffuser.response.search.SearchResponse
import org.techtown.diffuser.response.trend.TrendResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovie( //이벤트를 Flow로 처리하기위해  함수도 suspend 함수로 변경
        @Query("language") language : String,
        @Query("page") page : Int,
        @Query("region") region : String
    ) : PopularMoviesResponse  //이벤트를 Flow로 처리하기위해 리턴타입을 call 방식에서 레트로핏 Response 타입으로 변경

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovie(
        @Query("language") language : String,
        @Query("page") page : Int,
        @Query("region") region : String
    ) : NowPlayingResponse

    @GET("/3/movie/{movie_id}")
    suspend fun getDetailPage(
        @Path("movie_id") movieId : Long,
        @Query("language") language : String
    ) : DetailPage_3

    @GET("/3/movie/{movie_id}/credits")
    suspend fun getCast(
        @Path("movie_id") movieId : Long,
        @Query("language") language : String
    ) : CastResult

    @GET("/3/movie/upcoming")
    suspend fun getUpcomming(
        @Query("language") language : String,
        @Query("page") page : Int,
        @Query("region") region : String
    ) : Upcomming

    @GET("3/search/movie")
    suspend fun getSearch(
        @Query("language") language: String,
        @Query("page") page : Int,
        @Query("query") query: String,
    ) : SearchResponse

    @GET("3/trending/movie/day")
    suspend fun getTrend(
        @Query("language") language: String,
        @Query("page") page : Int,
    ) : TrendResponse
}