package org.techtown.diffuser.retrofit

import org.techtown.diffuser.response.PopularMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("/3/movie/popular")
    fun getPopularMovie(
        @Query("api_key") api_key : String,
        @Query("language") lnaguage : String,
        @Query("page") page : Int
    ) : Call<PopularMoviesResponse>
}