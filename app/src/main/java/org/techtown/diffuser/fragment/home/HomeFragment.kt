package org.techtown.diffuser.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.diffuser.activity.detailpage.PopularDetailActivity
import org.techtown.diffuser.databinding.ActivityHomeFragmentBinding
import org.techtown.diffuser.fragment.home.HomeAdapter.Companion.VIEW_TYPE_NOW_MOVIE
import org.techtown.diffuser.fragment.home.HomeAdapter.Companion.VIEW_TYPE_POPULAR_MOVIE
import org.techtown.diffuser.listener.OnFailureClickListener
import org.techtown.diffuser.listener.PopularClickListener
import org.techtown.diffuser.model.HorizontalMovieModel
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.model.Title
import org.techtown.diffuser.model.WrappingModel
import org.techtown.diffuser.response.nowplaying.NowPlayingResponse
import org.techtown.diffuser.response.PopularMoviesResponse
import org.techtown.diffuser.retrofit.RetrofitClient.Companion.retrofit
import org.techtown.diffuser.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    lateinit var binding: ActivityHomeFragmentBinding
    private lateinit var adapter: HomeAdapter

    private var service = retrofit.create(RetrofitInterface::class.java)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        val items = listOf(
            Title("인기영화", HomeAdapter.VIEW_TYPE_TITLE),
            WrappingModel(true, null, VIEW_TYPE_POPULAR_MOVIE),
            Title("상영중 영화", HomeAdapter.VIEW_TYPE_TITLE),
            WrappingModel(true, null, VIEW_TYPE_NOW_MOVIE)
        )
        adapter.addItems(items)
        fetch()
        fetch2()

    }

    private fun initView() {
        with(binding) {
            val layoutManager = LinearLayoutManager(context)
            adapter = HomeAdapter(object : PopularClickListener {
                override fun onClick(movie: Movie) {
                    Log.e("log", "initView() clicklistener 인터페이스 온클릭")
                    val intent = Intent(context, PopularDetailActivity::class.java)
                    intent.putExtra("movie_id", movie.id)
                    startActivity(intent)
                }

            }, object : OnFailureClickListener {
                override fun onClick(view: View, viewType: Int) {
                    Log.d("id", viewType.toString())
                    when (viewType) {
                        VIEW_TYPE_POPULAR_MOVIE -> {
                            fetch()
                        }
                        VIEW_TYPE_NOW_MOVIE -> {
                            fetch2()
                        }
                    }
                }

            })
            recyclerview.adapter = adapter
            recyclerview.layoutManager = layoutManager
        }
    }

    private fun fetch() {
        service.getPopularMovie(
            "ko",
            1
        ).enqueue(object : Callback<PopularMoviesResponse> {
            override fun onResponse(
                call: Call<PopularMoviesResponse>,
                response: Response<PopularMoviesResponse>
            ) {
                val result = response.body()

                val list = result!!.results.map {
                    Movie(
                        title = it.title,
                        rank = it.releaseDate,
                        imagePoster = it.posterPath,
                        id = it.id
                    )
                }
                val horizontalPopularModel =
                    HorizontalMovieModel(list, HomeAdapter.VIEW_TYPE_POPULAR_MOVIE)
                adapter.updatePopularWrappingModel(
                    WrappingModel(
                        isLoading = false,
                        model = horizontalPopularModel,
                        viewType = VIEW_TYPE_POPULAR_MOVIE
                    )
                )

            }

            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                Log.d("kmh", t.toString())

                adapter.updatePopularWrappingModel(
                    WrappingModel(
                        isLoading = false,
                        model = null,
                        viewType = VIEW_TYPE_POPULAR_MOVIE,
                        isFailure = true
                    )
                )
            }

        })
    }

    private fun fetch2() {
        service.getNowPlayingMovie(
            "ko",
            1
        ).enqueue(object : Callback<NowPlayingResponse> {
            override fun onResponse(
                call: Call<NowPlayingResponse>,
                response: Response<NowPlayingResponse>
            ) {
                val result = response.body()

                val list = result!!.results.map {
                    Movie(
                        title = it.title,
                        rank = it.releaseDate,
                        imageDrop = it.backdropPath,
                        id = it.id
                    )
                }

                val nowPlaying = HorizontalMovieModel(list, HomeAdapter.VIEW_TYPE_NOW_MOVIE)
                adapter.updateNowPlayingWrappingModel(
                    WrappingModel(
                        isLoading = false,
                        model = nowPlaying,
                        viewType = VIEW_TYPE_NOW_MOVIE
                    )
                )
            }

            override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {
                Log.d("kmh", t.toString())
                adapter.updateNowPlayingWrappingModel(
                    WrappingModel(
                        isLoading = false,
                        model = null,
                        viewType = VIEW_TYPE_NOW_MOVIE,
                        isFailure = true
                    )
                )
            }

        })

    }
}