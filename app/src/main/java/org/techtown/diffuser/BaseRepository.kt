package org.techtown.diffuser

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

open class BaseRepository {  //callApi 메서드는 매개변수로 suspend 람다 함수를 받으며  리턴타입으로 Flow Resource.Fail<Response> 등의 리턴을 받음. 케이스별로 분기처리.
    fun <Response : BaseResponse> callApi(responseFunction: suspend () -> Response): Flow<Resource<Response>> {
        return flow {
            emit(Resource.Loading())  // 로딩뷰를 위해 Resource.Loading() emit. fetch와 로직 짜서 로딩뷰 구현가능. //로딩 클래스 객체생성
            emit(
                safeResult(responseFunction)    //detailpage_3
            )
        }
    }

    suspend fun <Response : BaseResponse> safeResult(
        responseFunction: suspend () -> Response,
    ): Resource<Response> {
        return try {
            Resource.Success(responseFunction.invoke())  //성공부터 try 에러없이 통신 성공이면 여기서 종료.   detailpage_3
        } catch (e: HttpException) {   // 레트로핏 통신 실패시  HttpException 에러 타입인지 확인.
            Log.e("kmh!!!"  ,  "HttpException")
            Resource.Fail(ApiException.HttpException(code = e.code()))
        } catch (e: IOException) {  // HttpException 에러가 아니라면 IOException 에러인지 확인
            Log.e("kmh!!!"  ,  "IOException")
            Resource.Fail(ApiException.NetworkException)
        } catch (e: Exception) {  // 그 이외 에러
            Log.e("kmh!!!"  ,  "Exception : ${e.message}", e)
            Resource.Fail(ApiException.UnknownException)
        }
    }
}


sealed class Resource<out T> {  //flow 이벤트 처리를 위해 Flow 로 보내줄 데이터 타입을 작성. flow로 클래스를 흘려보내서 분기처리 할거임.
    class Success<T>(val model: T) : Resource<T>()   //model을 매개변수로받는.(detailpage_3)를 매개변수로받는.
    class Fail<T>(val exception: ApiException) : Resource<T>()
    class Loading<T> : Resource<T>()
}

sealed class ApiException {  // Fail 클래스에서 사용할 속성 클래스 정의.
    object NetworkException : ApiException()  //IOException 상황시 사용할 예외 object.  에러는 파일입출력시 파일이 없거나 네트워크 소켓으로부터 전송/수신을 할수 없는 경우 발생
    class HttpException(val code: Int) : ApiException() // HttpException 클래스에는 response, status상태코드를 나타내는 속성을 가지고 있음.  success=200   NotFound =404
    object UnknownException : ApiException()  // 그 이외 예외 상황 대비.
}