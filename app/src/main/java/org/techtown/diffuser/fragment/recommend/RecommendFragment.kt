package org.techtown.diffuser.fragment.recommend

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import org.techtown.diffuser.activity.detailpage.PopularDetailActivity
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.databinding.FragmentRecommendBinding
import org.techtown.diffuser.fragment.BaseFragment
import org.techtown.diffuser.fragment.recommend.bottomsheet.BottomSheetFragment

@AndroidEntryPoint
class RecommendFragment : BaseFragment<FragmentRecommendBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecommendBinding
        get() = FragmentRecommendBinding::inflate

    private lateinit var adapter: RecommendAdapter

    private val viewModel: RecommendViewModel by viewModels()
    private val bottomSheetFragment = BottomSheetFragment()

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
                itemClickListener = { view, viewType, movie, theMore ->
                    when (viewType) {
                        Constants.VIEW_TYPE_RECOMMEND_TITLE -> {
                            initBottomSheet()
                        }
                        Constants.VIEW_TYPE_RECOMMEND_ITEM -> {
                            val intent = Intent(context, PopularDetailActivity::class.java)
                            intent.putExtra(
                                "movie_id",
                                movie?.id
                            )
                            startActivity(intent)
                        }
                    }
                }
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
        bottomSheetFragment.show(childFragmentManager, tag)
    }

    companion object {
        fun newInstance(): Fragment {
            return RecommendFragment()
        }
    }

}