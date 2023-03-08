package org.techtown.diffuser.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.techtown.diffuser.Repository
import org.techtown.diffuser.RepositoryImpl
import org.techtown.diffuser.intercepter.ApiKeyIntercepter
import org.techtown.diffuser.retrofit.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {

    @Singleton
    @Provides
    fun provideRepository(service : RetrofitService) : Repository {
        return RepositoryImpl(service)
    }

    @Singleton
    @Provides
    fun provideService(retrofit : Retrofit) : RetrofitService {
        return  retrofit.create(RetrofitService::class.java)
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

