package org.techtown.diffuser.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.activity.detailpage.PopularDetailActivity
import org.techtown.diffuser.activity.moreview.comming.CommingMoreActivity
import org.techtown.diffuser.activity.moreview.nowplay.NowplayMoreActivity
import org.techtown.diffuser.activity.moreview.popular.PopularMoreActivity
import org.techtown.diffuser.clickInterface.MoreViewClick
import org.techtown.diffuser.databinding.ActivityHomeFragmentBinding
import org.techtown.diffuser.fragment.home.HomeAdapter.Companion.VIEW_TYPE_NOW_MOVIE
import org.techtown.diffuser.fragment.home.HomeAdapter.Companion.VIEW_TYPE_POPULAR_MOVIE
import org.techtown.diffuser.fragment.home.HomeAdapter.Companion.VIEW_TYPE_UPCOMMING

@AndroidEntryPoint
class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    lateinit var binding: ActivityHomeFragmentBinding
    private lateinit var adapter: HomeAdapter

    private val viewModel: HomeViewModel by viewModels()

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
        initObserver()
        fetchAll()
    }

    private fun initObserver() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }

    private fun initView() = with(binding) {
        swipe.setOnRefreshListener {
            onRefresh()
            swipe.isRefreshing = false
        }
        val layoutManager = LinearLayoutManager(context)

        adapter = HomeAdapter(
            itemClickListener = { _, viewType, movie ->
                if (movie != null) {
                    val intent = Intent(context, PopularDetailActivity::class.java)
                    intent.putExtra("movie_id", movie.id)
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
            },
            moreViewClick = object : MoreViewClick {
                override fun onClick(theMore: TheMore) {
                    when (theMore) {
                        TheMore.THEMORE_POPULAR -> {
                            val intent = Intent(context, PopularMoreActivity::class.java)
                            startActivity(intent)
                        }
                        TheMore.THEMORE_NOW -> {
                            val intent = Intent(context, NowplayMoreActivity::class.java)
                            startActivity(intent)
                        }
                        TheMore.THEMORE_COMMING -> {
                            val intent = Intent(context, CommingMoreActivity::class.java)
                            startActivity(intent)

                        }

                    }

                }
            })
        recyclerview.adapter = adapter
        recyclerview.layoutManager = layoutManager
    }

    private fun fetchAll() {
        viewModel.fetch()
        viewModel.fetch2()
        viewModel.fetchUpcomming()
    }

    override fun onRefresh() {
        fetchAll()
    }

    companion object {
        const val RECYCLERVIEW_ID_POPULAR = -1L
        const val RECYCLERVIEW_ID_TITME = -2L
        const val RECYCLERVIEW_ID_NOW = -3L
        const val RECYCLERVIEW_ID_COMMING = -4L
    }
}


enum class TheMore {
    THEMORE_POPULAR,
    THEMORE_NOW,
    THEMORE_COMMING
}
