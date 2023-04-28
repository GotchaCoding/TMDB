package org.techtown.diffuser.activity.moreview.nowplay

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.databinding.ActivityNowplayMoreViewBinding

@AndroidEntryPoint
class NowplayMoreActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    lateinit var binding: ActivityNowplayMoreViewBinding

    private lateinit var adapter: NowMoreAdapter

    private val viewModel: MoreNowplayViewModel by viewModels()

    lateinit var swipe: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNowplayMoreViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        viewModel.items.observe(this@NowplayMoreActivity) { items ->
            adapter.submitList(items)
        }
        viewModel.fetch()


    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = NowMoreAdapter()
        binding.recyclerviewTheMore.adapter = adapter
        binding.recyclerviewTheMore.layoutManager = layoutManager
        binding.recyclerviewTheMore.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItem = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (lastVisibleItem == totalItem - 2) {
                    viewModel.fetch()
                }
            }
        })

        swipe = binding.swipeNow
        swipe.setOnRefreshListener {
            onRefresh()
            swipe.isRefreshing = false
        }
    }

    override fun onRefresh() {
        viewModel.page = 1
        viewModel.fetch()
    }
}