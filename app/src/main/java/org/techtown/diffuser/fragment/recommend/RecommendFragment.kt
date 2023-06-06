package org.techtown.diffuser.fragment.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.manager.SupportRequestManagerFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.FragmentComponentManager
import org.techtown.diffuser.databinding.FragmentRecommendBinding
import org.techtown.diffuser.fragment.BaseFragment
import org.techtown.diffuser.fragment.recommend.bottomsheet.BottomSheetFragment

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
        initBottomSheet()
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

    private fun initBottomSheet() {
        val bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.show(childFragmentManager , tag)
    }

    companion object {
        fun newInstance(): Fragment {
            return RecommendFragment()
        }
    }

}