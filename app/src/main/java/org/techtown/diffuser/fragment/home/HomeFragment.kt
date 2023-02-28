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
        viewModel.fetch2()
        viewModel.fetchUpcomming()
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
                            viewModel.fetch2()
                        }
                        VIEW_TYPE_UPCOMMING -> {
                            viewModel.fetchUpcomming()
                        }
                    }
                }

            })
            recyclerview.adapter = adapter
            recyclerview.layoutManager = layoutManager
        }
    }





}