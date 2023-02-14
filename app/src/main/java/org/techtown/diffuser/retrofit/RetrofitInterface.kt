package org.techtown.diffuser.retrofit

import org.techtown.diffuser.response.DetailPage_3
import org.techtown.diffuser.response.NowPlayingResponse
import org.techtown.diffuser.response.PopularMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("/3/movie/popular")
    fun getPopularMovie(
        @Query("language") lnaguage : String,
        @Query("page") page : Int
    ) : Call<PopularMoviesResponse>

    @GET("/3/movie/now_playing")
    fun getNowPlayingMovie(
        @Query("language") language : String,
        @Query("page") page : Int
    ) : Call<NowPlayingResponse>


    @GET("/3/movie")
    fun getDetailPage(
        @Path("movie_id") movie_id : Int,
        @Query("language") language : String
    ) : Call<DetailPage_3>

}