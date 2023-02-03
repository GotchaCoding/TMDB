package org.techtown.diffuser.retrofit

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.techtown.diffuser.intercepter.ApiKeyIntercepter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    init {
         val client = OkHttpClient.Builder()
             .addInterceptor(ApiKeyIntercepter())
             .addInterceptor(HttpLoggingInterceptor().apply {
                 level = HttpLoggingInterceptor.Level.BODY
             })
             .build()


        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    companion object {

        lateinit var retrofit: Retrofit
            private set

        private const val baseUrl = "https://api.themoviedb.org"

        var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    field = RetrofitClient()
                }
                return field
            }
            private set

    }
}