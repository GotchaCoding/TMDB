package org.techtown.diffuser.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.techtown.diffuser.intercepter.ApiKeyIntercepter
import org.techtown.diffuser.retrofit.RetrofitInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {


    @Singleton
    @Provides
    fun retrofitInterface(retrofit : Retrofit) : RetrofitInterface {
        return  retrofit.create(RetrofitInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyIntercepter())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY})
            .build()

    }

}

