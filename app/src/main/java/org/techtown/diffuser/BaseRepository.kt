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


interface Repository {
    fun getPopular(): Flow<Resource<PopularMoviesResponse>>
    fun getNowPlay(): Flow<Resource<NowPlayingResponse>>
    fun getDetail(movieId : Int): Flow<Resource<DetailPage_3>>
    fun getCast(movieId : Int): Flow<Resource<CastResult>>
    fun getUpComming(): Flow<Resource<Upcomming>>
}

class RepositoryImpl @Inject constructor(
    private val service: RetrofitService
) : BaseRepository(), Repository {
    override fun getPopular(): Flow<Resource<PopularMoviesResponse>> = callApi {
        service.getPopularMovie("ko", 1, "KR")
    }

    override fun getNowPlay(): Flow<Resource<NowPlayingResponse>> = callApi {
        service.getNowPlayingMovie("ko", 1, "KR")
    }

    override fun getDetail(
        movieId : Int
    ): Flow<Resource<DetailPage_3>> = callApi {
        service.getDetailPage(movieId, "ko")
    }

    override fun getCast(
        movieId : Int
    ): Flow<Resource<CastResult>> = callApi {
        service.getCast(movieId, "ko")
    }

    override fun getUpComming(): Flow<Resource<Upcomming>> = callApi {
        service.getUpcomming("ko", 1, "KR")
    }


}

open class BaseResponse(
    @SerializedName("status_code")
    val statusCode: Int? = null,
    @SerializedName("status_message")
    val statusMessage: String? = null,
)

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