package org.techtown.diffuser.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
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
import org.techtown.diffuser.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding: ActivityHomeFragmentBinding
    private lateinit var adapter: HomeAdapter

    private val viewModel : HomeViewModel by viewModels()

    @Inject
  lateinit var service : RetrofitInterface
//    private var service = retrofit.create(RetrofitInterface::class.java)
//    private var items: List<ItemModel> = listOf()

    companion object {
        const val RECYCLERVIEW_ID_POPULAR = -1L
        const val RECYCLERVIEW_ID_TITME = -2L
        const val RECYCLERVIEW_ID_NOW = -3L
        const val RECYCLERVIEW_ID_COMMING = -4L
    }

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
        viewModel.fetch()
        fetch2()
        fetchUpcomming()
        Log.e("test","service infragmetn : $service")

        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
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
                            viewModel.fetch()
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
                val nowPlaying = HorizontalMovieModel(list, HomeAdapter.VIEW_TYPE_NOW_MOVIE, id = RECYCLERVIEW_ID_NOW)
                viewModel._items.value = viewModel.items.value!!.mapIndexed { index, itemModel ->
                    if (index == 3 && itemModel is WrappingModel) {
                        itemModel.copy(
                            isLoading = false,
                            model = nowPlaying,
                            viewType = VIEW_TYPE_NOW_MOVIE,
                            isFailure = false,
                            id = RECYCLERVIEW_ID_NOW
                        )
                    } else {
                        itemModel
                    }
                }
            }

            override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {
                Log.d("kmh", t.toString())
                viewModel._items.value = viewModel._items.value!!.mapIndexed { index, itemModel ->
                    if (index == 3 && itemModel is WrappingModel) {
                        itemModel.copy(
                            isLoading = false,
                            model = null,
                            viewType = VIEW_TYPE_NOW_MOVIE,
                            isFailure = true,
                            id = RECYCLERVIEW_ID_NOW
                        )
                    } else {
                        itemModel
                    }
                }
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
                    HorizontalMovieModel(list, HomeAdapter.VIEW_TYPE_UPCOMMING, id= RECYCLERVIEW_ID_COMMING)
                Log.d("testtest" , horizontalPopularModel.id.toString())
                Log.d("testtest2" , (horizontalPopularModel as ItemModel).id.toString())

                viewModel._items.value = viewModel._items.value!!.mapIndexed { index, itemModel ->
                    if (index == 5 && itemModel is WrappingModel) {
                        itemModel.copy(
                            isLoading = false,
                            model = horizontalPopularModel,
                            viewType = VIEW_TYPE_UPCOMMING,
                            isFailure = false,
                            id = RECYCLERVIEW_ID_COMMING
                        )
                    } else {
                        itemModel
                    }
                }
            }

            override fun onFailure(call: Call<Upcomming>, t: Throwable) {
                viewModel._items.value = viewModel._items.value!!.mapIndexed { index, itemModel ->
                    if (index == 5 && itemModel is WrappingModel) {
                        itemModel.copy(
                            isLoading = false,
                            model = null,
                            viewType = VIEW_TYPE_UPCOMMING,
                            isFailure = true,
                            id = RECYCLERVIEW_ID_COMMING
                        )
                    } else {
                        itemModel
                    }
                }
            }

        })
    }

}