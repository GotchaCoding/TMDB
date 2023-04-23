package org.techtown.diffuser.fragment.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.R
import org.techtown.diffuser.databinding.ActivitySearchFragmentBinding

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var binding: ActivitySearchFragmentBinding

    private lateinit var adapter: SearchAdapter

    private val viewModel: SearchViewModel by viewModels()

    lateinit var oneTitle: String
    lateinit var twoTitle: String
    lateinit var threeTitle: String
    lateinit var fourTitle: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivitySearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        initTrendObserver()
        viewModel.fetch("")
        viewModel.fetchTrend()
        titleClick()
    }

    private fun initObserver() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }

    private fun initTrendObserver() {
        viewModel.trendItems.observe(viewLifecycleOwner) {
            var size = viewModel.trendItems.value!!.size
            if (size >= 1) {
                oneTitle = viewModel.trendItems.value!!.get(0).title
                twoTitle = viewModel.trendItems.value!!.get(1).title
                threeTitle = viewModel.trendItems.value!!.get(2).title
                fourTitle = viewModel.trendItems.value!!.get(3).title
                binding.tvFirst.text = oneTitle
                binding.tvSecond.text = twoTitle
                binding.tvThird.text = threeTitle
                binding.tvFour.text = fourTitle
            }
        }
    }

    private fun initView() {
        with(binding) {
            val layoutManager = LinearLayoutManager(context)
            adapter = SearchAdapter()
            recyclerviewTheMore.adapter = adapter
            recyclerviewTheMore.layoutManager = layoutManager

            edtSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                    if (p1 == EditorInfo.IME_ACTION_DONE) {
                        var title: String = edtSearch.text.toString()
                        viewModel.fetch(title)
                        tvHint.isVisible = false
                        return true
                    }
                    return false
                }
            })
            btnSearch.setOnClickListener {
                var title: String = edtSearch.text.toString()
                viewModel.fetch(title)
                tvHint.isVisible = false
            }
            tvHint.bringToFront()
        }
    }

    fun titleClick() = with(binding) {
        tvFirst.setOnClickListener {
            edtSearch.setText(oneTitle)
        }
        tvSecond.setOnClickListener {
            edtSearch.setText(threeTitle)
        }
        tvThird.setOnClickListener {
            edtSearch.setText(twoTitle)
        }
        tvFour.setOnClickListener {
            edtSearch.setText(fourTitle)
        }
    }
    
}