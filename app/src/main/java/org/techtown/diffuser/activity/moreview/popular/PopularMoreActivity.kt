package org.techtown.diffuser.activity.moreview.popular

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.R
import org.techtown.diffuser.activity.detailpage.DetailAdapter
import org.techtown.diffuser.databinding.ActivityPopularMoreViewBinding


@AndroidEntryPoint
class PopularMoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityPopularMoreViewBinding

    private lateinit var adapter: PopularMoreAdapter

    private val viewModel: MorePopularViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPopularMoreViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        viewModel.items.observe(this@PopularMoreActivity) { items ->
            adapter.submitList(items)
        }
        viewModel.fetch()
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
}