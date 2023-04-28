package org.techtown.diffuser.activity.moreview.popular

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.databinding.ActivityPopularMoreViewBinding


@AndroidEntryPoint
class PopularMoreActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    lateinit var binding: ActivityPopularMoreViewBinding

    private lateinit var adapter: PopularMoreAdapter

    private val viewModel: MorePopularViewModel by viewModels()

    lateinit var swipe: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPopularMoreViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        viewModel.items.observe(this@PopularMoreActivity) { items ->
            adapter.submitList(items)
        }
        viewModel.fetch()

        swipe = binding.swipePopular
        swipe.setOnRefreshListener {
            onRefresh()
            swipe.isRefreshing = false
        }
    }


    private fun initView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = PopularMoreAdapter()
        binding.recyclerviewTheMore.adapter = adapter
        binding.recyclerviewTheMore.layoutManager = layoutManager
        binding.recyclerviewTheMore.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItem = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (lastVisibleItem == totalItem - 3) {
                    viewModel.fetch()
                }
            }
        })

    }

    override fun onRefresh() {
        viewModel.page = 1
        viewModel.fetch()
    }
}