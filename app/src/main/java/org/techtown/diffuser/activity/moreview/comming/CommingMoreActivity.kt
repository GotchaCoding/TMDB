package org.techtown.diffuser.activity.moreview.comming

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.activity.detailpage.PopularDetailActivity
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.databinding.ActivityCommingMoreViewBinding

@AndroidEntryPoint
class CommingMoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityCommingMoreViewBinding

    private lateinit var adapter: CommingMoreAdapter

    private val viewModel: MoreComViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommingMoreViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        viewModel.items.observe(this) {
            adapter.submitList(it)
        }
        viewModel.fetch()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = CommingMoreAdapter(
            itemClickListener = { _, viewType, movie, _ ->
                when (viewType) {
                    Constants.VIEW_TYPE_FAIL -> {
                        viewModel.fetch()
                    }
                    else -> {
                        movie?.let {
                            val intent = Intent(this, PopularDetailActivity::class.java)
                            intent.putExtra(
                                "movie_id",
                                movie.id
                            )
                            startActivity(intent)
                        }
                    }
                }
            })
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