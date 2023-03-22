package org.techtown.diffuser

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.techtown.diffuser.response.Upcomming
import org.techtown.diffuser.response.detail.cast.CastResult
import org.techtown.diffuser.response.detail.detailmovie.DetailPage_3
import org.techtown.diffuser.response.nowplaying.NowPlayingResponse
import org.techtown.diffuser.response.pupular.PopularMoviesResponse
import org.techtown.diffuser.retrofit.RetrofitService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

open class BaseRepository {
    fun <Response : BaseResponse> callApi(responseFunction: suspend () -> Response): Flow<Resource<Response>> {
        return flow {
            emit(Resource.Loading())
            emit(
                safeResult {
                    responseFunction.invoke()
                }
            )
        }
    }

    suspend fun <Response : BaseResponse> safeResult(
        responseFunction: suspend () -> Response,
    ): Resource<Response> {
        return try {
            Resource.Success(responseFunction.invoke())
        } catch (e: HttpException) {
            Resource.Fail(ApiException.HttpException(code = e.code()))
        } catch (e: IOException) {
            Resource.Fail(ApiException.NetworkException)
        } catch (e: Exception) {
            Resource.Fail(ApiException.UnknownException)
        }
    }
}


sealed class Resource<out T> {
    class Success<T>(val model: T) : Resource<T>()
    class Fail<T>(val exception: ApiException) : Resource<T>()
    class Loading<T> : Resource<T>()
}

sealed class ApiException {
    object NetworkException : ApiException()
    class HttpException(val code: Int) : ApiException()
    object UnknownException : ApiException()
}