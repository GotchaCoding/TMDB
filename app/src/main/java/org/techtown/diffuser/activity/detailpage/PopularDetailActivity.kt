package org.techtown.diffuser.activity.detailpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.databinding.ActivityPopualrDetailBinding
import org.techtown.diffuser.listener.MovieClickListener

@AndroidEntryPoint
class PopularDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityPopualrDetailBinding

    private lateinit var adapter: DetailAdapter


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

        adapter = DetailAdapter(MovieClickListener() { view, viewType, movie ->
            when (viewType) {
                DetailAdapter.VIEW_TYPE_DETAIL_BACKGROND -> {
                    viewModel.fetch()
                }
                DetailAdapter.VIEW_TYPE_DETAIL_CASTING -> {
                    viewModel.fetchCast()
                }
            }

        })
        binding.recyclerviewDetail.adapter = adapter
        binding.recyclerviewDetail.layoutManager = layoutManager

    }

}
