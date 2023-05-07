package org.techtown.diffuser.activity.detailpage

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.databinding.ActivityPopualrDetailBinding
import org.techtown.diffuser.fragment.home.TheMore

@AndroidEntryPoint
class PopularDetailActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    lateinit var binding: ActivityPopualrDetailBinding

    private lateinit var adapter: DetailAdapter

    lateinit var swipe: SwipeRefreshLayout

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPopualrDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        viewModel.fetch()
        viewModel.fetchCast()
//        viewModel.items.observe(this, Observer<List<ItemModel>> {test:List<ItemModel> -> adapter.submitList(test) })
        viewModel.items.observe(this@PopularDetailActivity) { items ->
            adapter.submitList(items)
        }
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = DetailAdapter { view, viewType, movie, _ : TheMore?->
            when (viewType) {
                DetailAdapter.VIEW_TYPE_DETAIL_BACKGROND -> {
                    viewModel.fetch()
                }
                DetailAdapter.VIEW_TYPE_DETAIL_CASTING -> {
                    viewModel.fetchCast()
                }
            }
        }
        binding.recyclerviewDetail.adapter = adapter
        binding.recyclerviewDetail.layoutManager = layoutManager

        swipe = binding.swipe
        swipe.setOnRefreshListener {
            onRefresh()
            swipe.isRefreshing = false
        }
    }

    fun fetchAll() {
        viewModel.fetch()
        viewModel.fetchCast()
    }

    override fun onRefresh() {
        fetchAll()
    }
}
