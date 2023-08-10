package org.techtown.diffuser.activity.detailpage

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.R
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.databinding.ActivityPopualrDetailBinding
import org.techtown.diffuser.fragment.ItemClickListener
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.Movie

@AndroidEntryPoint
class PopularDetailActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    lateinit var binding: ActivityPopualrDetailBinding

    private lateinit var adapter: DetailAdapter

    lateinit var swipe: SwipeRefreshLayout

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_popualr_detail)
        initView()
        initObserver()
        fetchAll()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = DetailAdapter(itemClickListener = object : ItemClickListener {
            override fun onItemClick(view: View, viewType: Int, movie: Movie?, theMore: TheMore?) {
                when (viewType) {
                    Constants.VIEW_TYPE_DETAIL_BACKGROND -> {
                        viewModel.fetch()
                    }

                    Constants.VIEW_TYPE_DETAIL_CASTING -> {
                        viewModel.fetchCast()
                    }
                }
            }
        })
        binding.recyclerviewDetail.adapter = adapter
        binding.recyclerviewDetail.layoutManager = layoutManager

        swipe = binding.swipe
        swipe.setOnRefreshListener {
            onRefresh()
            swipe.isRefreshing = false
        }
    }

    private fun initObserver() {
        viewModel.items.observe(this@PopularDetailActivity) { items ->
            adapter.submitList(items)
        }
    }

    private fun fetchAll() {
        viewModel.fetch()
        viewModel.fetchCast()
    }

    override fun onRefresh() {
        fetchAll()
    }
}
