package org.techtown.diffuser.activity.detailpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.databinding.ActivityPopualrDetailBinding
import org.techtown.diffuser.listener.OnFailureClickListener
import org.techtown.diffuser.model.*
import org.techtown.diffuser.response.detail.cast.CastResult
import org.techtown.diffuser.response.detail.detailmovie.DetailPage_3
import org.techtown.diffuser.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class PopularDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityPopualrDetailBinding

    private lateinit var adapter: DetailAdapter

    @Inject
    lateinit var service : RetrofitInterface
    var movieId: Int = 0

    private var items: List<ItemModel> = listOf()

    companion object {
        const val RECYCLERVIEW_ID_TOP_IN_TOP = -1L
        const val RECYCLERVIEW_ID_TOP = -2L
        const val RECYCLERVIEW_ID_TITLE = -3L
        const val RECYCLERVIEW_ID_CAST = -4L

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPopualrDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieId = intent.getIntExtra("movie_id", 0)
        initView()

        val topModel = DetailTopModel(
            "",
            "",
            "",
            "",
            id = RECYCLERVIEW_ID_TOP_IN_TOP
        )
        val defaultList = listOf(
            WrappingDetailModel(
                true,
                null,
                topModel,
                DetailAdapter.VIEW_TYPE_DETAIL_BACKGROND,
                isFailure = false,
                id = RECYCLERVIEW_ID_TOP
            ),
            Title("캐스팅", DetailAdapter.VIEW_TYPE_DETAIL_TITLE, RECYCLERVIEW_ID_TITLE),
            WrappingDetailModel(
                true,
                null,
                null,
                DetailAdapter.VIEW_TYPE_DETAIL_CASTING,
                false,
                RECYCLERVIEW_ID_CAST
            )
        )
        items = defaultList
        adapter.submitList(items)
        fetch()
        fetchCast()

    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = DetailAdapter(object : OnFailureClickListener {
            override fun onClick(view: View, viewType: Int) {
                when (viewType) {
                    DetailAdapter.VIEW_TYPE_DETAIL_BACKGROND -> {
                        fetch()
                    }
                    DetailAdapter.VIEW_TYPE_DETAIL_CASTING -> {
                        fetchCast()
                    }
                }
            }

        })
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
                    items = items.mapIndexed { index, itemModel ->
                        if (index == 0 && itemModel is WrappingDetailModel) {
                            Log.d("kmh2" , "fetch on response")
                            itemModel.copy(
                                isLoading = false,
                                castModel = null,
                                detailTopModel = DetailTopModel(
                                    title = result.title,
                                    overview = result.overview,
                                    postUrl = result.posterPath,
                                    backDropUrl = result.backdropPath,
                                    id = RECYCLERVIEW_ID_TOP_IN_TOP,
                                    isfailure = false
                                ),
                                viewType = DetailAdapter.VIEW_TYPE_DETAIL_BACKGROND,
                                isFailure = false,
                                id = RECYCLERVIEW_ID_TOP


                            )

                        } else {
                            itemModel
                        }
                    }
                    adapter.submitList(items)
                    Log.d("back" , "success")
                }
            }

            override fun onFailure(call: Call<DetailPage_3>, t: Throwable) {
                Log.d("kmh", t.toString())
                items = items.mapIndexed { index, itemModel ->
                    if (index == 0 && itemModel is WrappingDetailModel) {
                        itemModel.copy(
                            isLoading = false,
                            castModel = null,
                            detailTopModel = DetailTopModel(
                                title = null,
                                overview = "",
                                postUrl = "",
                                backDropUrl = "",
                                isfailure = true,
                                id = RECYCLERVIEW_ID_TOP_IN_TOP,
                                isLoading = false,
                            ),
                            viewType = DetailAdapter.VIEW_TYPE_DETAIL_BACKGROND,
                            isFailure = true,
                            id = RECYCLERVIEW_ID_TOP

                        )
                    } else {
                        itemModel
                    }
                }
                adapter.submitList(items)
                Log.d("back" , "faic")
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
                    }.filter {
                        it.imgActor != null
                    }

                    val castModel =
                        HorizontalCastModel(
                            id = RECYCLERVIEW_ID_CAST,
                            list,
                            DetailAdapter.VIEW_TYPE_DETAIL_CASTING
                        )
                    items = items.mapIndexed { index, itemModel ->
                        if (index == 2 && itemModel is WrappingDetailModel) {
                            itemModel.copy(
                                isLoading = false,
                                castModel = castModel,
                                detailTopModel = null,
                                viewType = DetailAdapter.VIEW_TYPE_DETAIL_CASTING,
                                isFailure = false
                            )
                        } else {
                            itemModel
                        }
                    }
                    adapter.submitList(items)
                }
            }

            override fun onFailure(call: Call<CastResult>, t: Throwable) {
                Log.d("kmh", t.toString())
                items = items.mapIndexed { index, itemModel ->
                    if (index == 2 && itemModel is WrappingDetailModel) {
                        itemModel.copy(
                            isLoading = false,
                            castModel = null,
                            detailTopModel = null,
                            viewType = DetailAdapter.VIEW_TYPE_DETAIL_CASTING,
                            isFailure = true
                        )
                    } else {
                        itemModel
                    }
                }
                adapter.submitList(items)
            }

        })
    }
}
