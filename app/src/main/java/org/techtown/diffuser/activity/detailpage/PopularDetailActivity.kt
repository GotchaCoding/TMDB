package org.techtown.diffuser.activity.detailpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.diffuser.databinding.ActivityPopualrDetailBinding
import org.techtown.diffuser.model.*
import org.techtown.diffuser.response.detail.cast.CastResult
import org.techtown.diffuser.response.detail.detailmovie.DetailPage_3
import org.techtown.diffuser.retrofit.RetrofitClient
import org.techtown.diffuser.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityPopualrDetailBinding
    private lateinit var adapter: DetailAdapter

    private var service = RetrofitClient.retrofit.create(RetrofitInterface::class.java)
    var movieId: Int = 0

    lateinit var mutableCastList: MutableList<CastRv>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPopualrDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieId = intent.getIntExtra("movie_id", 0)
        Log.e("kyh!!!", "movieId : $movieId")
        initView()

        val items = listOf(
            DetailTopModel("", "", "", "", DetailAdapter.VIEW_TYPE_DETAIL_BACKGROND),
            Title("캐스팅" , DetailAdapter.VIEW_TYPE_DETAIL_TITLE),
            WrappingDetailModel(true, null, null, DetailAdapter.VIEW_TYPE_DETAIL_CASTING)
        )
        adapter.addItem(items)
        fetch()
        fetchCast()

    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = DetailAdapter()
        binding.recyclerviewDetail.adapter = adapter
        binding.recyclerviewDetail.layoutManager = layoutManager

    }

    private fun fetch() {
        service.getDetailPage(
            movieId,
            "ko"
        ).enqueue(object : Callback<DetailPage_3> {
            override fun onResponse(call: Call<DetailPage_3>, response: Response<DetailPage_3>) {
                val result = response.body()
                if (result != null) {
                    adapter.updateTopModel(
                        DetailTopModel(
                            title = result.title,
                            overview = result.overview,
                            postUrl = result.posterPath,
                            backDropUrl = result.backdropPath,
                            viewType = DetailAdapter.VIEW_TYPE_DETAIL_BACKGROND
                        )
                    )
                }
            }

            override fun onFailure(call: Call<DetailPage_3>, t: Throwable) {
                Log.d("kmh", t.toString())

            }


        })
    }

    private fun fetchCast() {
        service.getCast(
            movieId,
            "ko"
        ).enqueue(object : Callback<CastResult> {
            override fun onResponse(call: Call<CastResult>, response: Response<CastResult>) {
                val result = response.body()
                if (result != null) {
                    val list = result!!.cast.map {
                        CastRv(
                            imgActor = it.profilePath,
                            castChracter = it.character,
                            castName = it.name
                        )
                    }

                    /**  사진삭제
                    Cast api 에서 사진 null 일경우 리스트 삭제를 위해
                     HorizontalCastModel에서 list를 MUTABLELIST로 봐꾸고
                     null값인 포지션을 변수 num_forReove 에 저장
                     */
                    var num_forRemove = arrayListOf<Int>()   // 사진이 널값인 position 저장.
                    mutableCastList = list.toMutableList()  // list 를 mutable 로 변경
                    for (i: Int in 0..mutableCastList.size - 1) {
                        if (mutableCastList[i].imgActor == null) {
                            num_forRemove.add(i)
                        }
                    }

                    num_forRemove.reverse()  // 역순으로 봐꺼주지 않으면 앞에서부터 제거하기 때문에 인덱스 오류 발생.

                    for (i: Int in 0..num_forRemove.size - 1) {
                        mutableCastList.removeAt(num_forRemove[i])
                    }

                    val castModel =
                        HorizontalCastModel(mutableCastList, DetailAdapter.VIEW_TYPE_DETAIL_CASTING)
                    adapter.updateCastWrappingModel(
                        WrappingDetailModel(
                            isLoading = false,
                            castModel = castModel,
                            detailTopModel = null,
                            viewType = DetailAdapter.VIEW_TYPE_DETAIL_CASTING
                        )
                    )
                }
            }

            override fun onFailure(call: Call<CastResult>, t: Throwable) {
                Log.d("kmh", t.toString())
            }

        })
    }
}
