package org.techtown.diffuser.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.activity.detailpage.PopularDetailActivity
import org.techtown.diffuser.databinding.ActivityHomeFragmentBinding
import org.techtown.diffuser.fragment.home.HomeAdapter.Companion.VIEW_TYPE_NOW_MOVIE
import org.techtown.diffuser.fragment.home.HomeAdapter.Companion.VIEW_TYPE_POPULAR_MOVIE
import org.techtown.diffuser.fragment.home.HomeAdapter.Companion.VIEW_TYPE_UPCOMMING
import org.techtown.diffuser.retrofit.RetrofitService
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding: ActivityHomeFragmentBinding
    private lateinit var adapter: HomeAdapter

    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var service: RetrofitService
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
        Log.e("test", "service infragmetn : $service")

        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }


    /**
     **  클릭리스너 --> 람다
     */



    private fun initView() {
        with(binding) {
            val layoutManager = LinearLayoutManager(context)


            adapter = HomeAdapter { view, viewType, movie ->
                if (movie != null) {
                    val intent = Intent(context, PopularDetailActivity::class.java)
                    intent.putExtra("movie_id", movie?.idNum)
                    startActivity(intent)
                }

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
            recyclerview.adapter = adapter
            recyclerview.layoutManager = layoutManager
        }


    }
}