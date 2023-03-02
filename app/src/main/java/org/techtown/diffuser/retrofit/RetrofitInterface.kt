package org.techtown.diffuser.retrofit

import org.techtown.diffuser.response.Upcomming
import org.techtown.diffuser.response.detail.detailmovie.DetailPage_3
import org.techtown.diffuser.response.nowplaying.NowPlayingResponse
import org.techtown.diffuser.response.pupular.PopularMoviesResponse
import org.techtown.diffuser.response.detail.cast.CastResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("/3/movie/popular")
    fun getPopularMovie(
        @Query("language") lnaguage : String,
        @Query("page") page : Int,
        @Query("region") region : String
    ) : Call<PopularMoviesResponse>

    @GET("/3/movie/now_playing")
    fun getNowPlayingMovie(
        @Query("language") language : String,
        @Query("page") page : Int,
        @Query("region") region : String
    ) : Call<NowPlayingResponse>


    @GET("/3/movie/{movie_id}")
    fun getDetailPage(
        @Path("movie_id") movie_id : Int,
        @Query("language") language : String
    ) : Call<DetailPage_3>

    @GET("/3/movie/{movie_id}/credits")
    fun getCast(
        @Path("movie_id") movie_id : Int,
        @Query("language") language : String
    ) : Call<CastResult>

    @GET("/3/movie/upcoming")
    fun getUpcomming(
        @Query("language") language : String,
        @Query("page") page : Int,
        @Query("region") region : String
    ) : Call<Upcomming>
}