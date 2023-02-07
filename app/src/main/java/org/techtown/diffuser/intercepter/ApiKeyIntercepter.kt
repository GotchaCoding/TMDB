package org.techtown.diffuser.intercepter

import okhttp3.Interceptor
import okhttp3.Response
import org.techtown.diffuser.BuildConfig
import org.techtown.diffuser.application.DiffuserApp

class ApiKeyIntercepter : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val addedUrl =
            originalRequest.url.newBuilder().addQueryParameter("api_key", BuildConfig.TMDB_API_KEY).build()

        val finalRequest = originalRequest.newBuilder()
            .url(addedUrl)
            .method(originalRequest.method, originalRequest.body)
            .build()

        return chain.proceed(finalRequest)
    }
}