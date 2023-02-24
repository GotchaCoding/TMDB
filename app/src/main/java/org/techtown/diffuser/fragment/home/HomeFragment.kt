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
import org.techtown.diffuser.fragment.home.HomeAdapter.Companion.VIEW_TYPE_UPCOMMING
import org.techtown.diffuser.listener.OnFailureClickListener
import org.techtown.diffuser.listener.PopularClickListener
import org.techtown.diffuser.model.*
import org.techtown.diffuser.response.Upcomming
import org.techtown.diffuser.response.nowplaying.NowPlayingResponse
import org.techtown.diffuser.response.pupular.PopularMoviesResponse
import org.techtown.diffuser.retrofit.RetrofitClient.Companion.retrofit
import org.techtown.diffuser.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    lateinit var binding: ActivityHomeFragmentBinding
    private lateinit var adapter: HomeAdapter

    private var service = retrofit.create(RetrofitInterface::class.java)
    private var items: List<ItemModel> = listOf()

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
        val defaultList = listOf(
            Title("인기영화", HomeAdapter.VIEW_TYPE_TITLE),
            WrappingModel(true, null, VIEW_TYPE_POPULAR_MOVIE),
            Title("상영중 영화", HomeAdapter.VIEW_TYPE_TITLE),
            WrappingModel(true, null, VIEW_TYPE_NOW_MOVIE),
            Title("개봉 예정", HomeAdapter.VIEW_TYPE_TITLE),
            WrappingModel(true, null, VIEW_TYPE_POPULAR_MOVIE)
        )
        items = defaultList
        adapter.submitList(items)
        fetch()
        fetch2()
        fetchUpcomming()
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
                        VIEW_TYPE_UPCOMMING -> {
                            fetchUpcomming()
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

                items =
                    items.mapIndexed { index, itemModel ->
                        if (index == 1 && itemModel is WrappingModel) {
                            itemModel.copy(
                                isLoading = false,
                                model = horizontalPopularModel,
                                viewType = VIEW_TYPE_POPULAR_MOVIE
                            )
                        } else {
                            itemModel
                        }
                    }
                adapter.submitList(items)
            }

            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                Log.d("kmh", t.toString())
                items = items.mapIndexed { index, itemModel ->
                    if (index == 1 && itemModel is WrappingModel) {
                        itemModel.copy(
                            isLoading = false,
                            model = null,
                            viewType = VIEW_TYPE_POPULAR_MOVIE,
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
                items = items.mapIndexed { index, itemModel ->
                    if (index == 3 && itemModel is WrappingModel) {
                        itemModel.copy(
                            isLoading = false,
                            model = nowPlaying,
                            viewType = VIEW_TYPE_NOW_MOVIE
                        )
                    } else {
                        itemModel
                    }
                }
                adapter.submitList(items)
            }

            override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {
                Log.d("kmh", t.toString())
                items = items.mapIndexed { index, itemModel ->
                    if (index == 3 && itemModel is WrappingModel) {
                        itemModel.copy(
                            isLoading = false,
                            model = null,
                            viewType = VIEW_TYPE_NOW_MOVIE,
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

    private fun fetchUpcomming() {
        service.getUpcomming(
            language = "ko",
            page = 1,
            region = "KR"
        ).enqueue(object : Callback<Upcomming> {
            override fun onResponse(call: Call<Upcomming>, response: Response<Upcomming>) {
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
                    HorizontalMovieModel(list, HomeAdapter.VIEW_TYPE_UPCOMMING)

                items = items.mapIndexed { index, itemModel ->
                    if (index == 5 && itemModel is WrappingModel) {
                        itemModel.copy(
                            isLoading = false,
                            model = horizontalPopularModel,
                            viewType = VIEW_TYPE_UPCOMMING
                        )
                    } else {
                        itemModel
                    }
                }
                adapter.submitList(items)
            }

            override fun onFailure(call: Call<Upcomming>, t: Throwable) {
                items = items.mapIndexed { index, itemModel ->
                    if(index ==5 && itemModel is WrappingModel) {
                        itemModel.copy(
                            isLoading = false,
                            model = null,
                            viewType = VIEW_TYPE_UPCOMMING,
                            isFailure = true
                        )
                    }else {
                        itemModel
                    }
                }

            }

        })
    }

}