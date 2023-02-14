package org.techtown.diffuser.activity.detailpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.diffuser.R
import org.techtown.diffuser.databinding.ActivityPopualrDetailBinding
import org.techtown.diffuser.fragment.home.HomeAdapter
import org.techtown.diffuser.model.DetailTopModel
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.response.DetailPage_3
import org.techtown.diffuser.response.PopularMoviesResponse
import org.techtown.diffuser.retrofit.RetrofitClient
import org.techtown.diffuser.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityPopualrDetailBinding
    private lateinit var adapter: DetailAdapter

    private var service = RetrofitClient.retrofit.create(RetrofitInterface::class.java)
    lateinit var movie: Movie
    var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPopualrDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieId = intent.getIntExtra("movie_id", 0)
        Log.e("kyh!!!", "movieId : $movieId")
        initView()

        fetch()

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
                    adapter.setTopModel(
                        DetailTopModel(
                            title= result.title,
                            overview = result.overview,
                            postUrl = result.posterPath,
                            backDropUrl = result.backdropPath,
                            viewType = DetailAdapter.VIEW_TYPE_DETAIL_BACKGROND
                        )
                    )
                }
            }

            override fun onFailure(call: Call<DetailPage_3>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }
}
