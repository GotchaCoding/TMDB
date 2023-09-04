package org.techtown.diffuser.fragment.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.databinding.FragmentDetailBinding
import org.techtown.diffuser.fragment.BaseFragment
import org.techtown.diffuser.fragment.ItemClickListener
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.Movie

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(), SwipeRefreshLayout.OnRefreshListener {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    private lateinit var adapter: DetailAdapter
    lateinit var swipe: SwipeRefreshLayout
    private val viewModel: DetailViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        fetchAll()
    }

    private fun initView() {


        val layoutManager = LinearLayoutManager(context)


        adapter = DetailAdapter(itemClickListener = object : ItemClickListener {
            override fun onItemClick(view: View, viewType: Int, movie: Movie?, theMore: TheMore?) {
                when (viewType) {
                    Constants.VIEW_TYPE_DETAIL_BACKGROND -> {
                        viewModel.fetch()
                    }

                    Constants.VIEW_TYPE_DETAIL_CASTING -> {
                        viewModel.fetchCast()
                    }
                    Constants.VIEW_TYPE_WEBVIEW -> {
                        Log.e("kmh!!!" , " clicklistener test :Constants.VIEW_TYPE_WEBVIEW ")
                        viewModel.fetchVideo()
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
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }

    private fun fetchAll() {
        viewModel.fetch()
        viewModel.fetchCast()
        viewModel.fetchVideo()
    }

    override fun onRefresh() {
        fetchAll()
    }
}