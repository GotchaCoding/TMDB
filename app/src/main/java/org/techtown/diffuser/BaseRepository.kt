package org.techtown.diffuser

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

open class BaseRepository {
    fun <Response : BaseResponse> callApi(responseFunction: suspend () -> Response): Flow<Resource<Response>> {
        return flow {
            emit(Resource.Loading())
            emit(
                safeResult(responseFunction)  //detailpage_3
            )
        }
    }

    suspend fun <Response : BaseResponse> safeResult(
        responseFunction: suspend () -> Response,
    ): Resource<Response> {
        return try {
            Resource.Success(responseFunction.invoke())  //detailpage_3
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
    class Success<T>(val model: T) : Resource<T>()   //model을 매개변수로받는.(detailpage_3)를 매개변수로받는.
    class Fail<T>(val exception: ApiException) : Resource<T>()
    class Loading<T> : Resource<T>()
}

sealed class ApiException {
    object NetworkException : ApiException()
    class HttpException(val code: Int) : ApiException()
    object UnknownException : ApiException()
}