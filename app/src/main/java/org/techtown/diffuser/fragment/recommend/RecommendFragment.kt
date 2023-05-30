package org.techtown.diffuser.fragment.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.databinding.FragmentRecommendBinding
import org.techtown.diffuser.fragment.BaseFragment

@AndroidEntryPoint
class RecommendFragment : BaseFragment<FragmentRecommendBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecommendBinding
        get() = FragmentRecommendBinding::inflate

    private lateinit var adapter: RecommendAdapter

    private val viewModel: RecommendViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        viewModel.fetch()
    }

    private fun initView() {
        binding.apply {
            val layoutManager = GridLayoutManager(context, 2)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val size = if (position == 0) {
                        2
                    } else {
                        1
                    }
                    return size
                }
            }
            adapter = RecommendAdapter(
                itemClickListener = { _, viewType, movie, theMore -> }
            )

            rvMain.adapter = adapter
            rvMain.layoutManager = layoutManager
        }
    }

    private fun initObserver() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return RecommendFragment()
        }
    }

}