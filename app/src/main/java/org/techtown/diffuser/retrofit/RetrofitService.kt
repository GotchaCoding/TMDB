package org.techtown.diffuser.retrofit

import org.techtown.diffuser.response.Upcomming
import org.techtown.diffuser.response.detail.cast.CastResult
import org.techtown.diffuser.response.detail.detailmovie.DetailPage_3
import org.techtown.diffuser.response.nowplaying.NowPlayingResponse
import org.techtown.diffuser.response.pupular.PopularMoviesResponse
import org.techtown.diffuser.response.search.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovie(
        @Query("language") language : String,
        @Query("page") page : Int,
        @Query("region") region : String
    ) : PopularMoviesResponse

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
    suspend fun  getSearch(
        @Query("language") language: String,
        @Query("page") page : Int,
        @Query("query") query: String,
    ) : SearchResponse
}