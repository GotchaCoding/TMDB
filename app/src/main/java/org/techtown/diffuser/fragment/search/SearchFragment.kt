package org.techtown.diffuser.fragment.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.R
import org.techtown.diffuser.activity.detailpage.PopularDetailActivity
import org.techtown.diffuser.con.Constants
import org.techtown.diffuser.databinding.ActivitySearchFragmentBinding
import org.techtown.diffuser.fragment.recommend.RecommendFragment

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var binding: ActivitySearchFragmentBinding

    private lateinit var adapter: SearchAdapter

    private val viewModel: SearchViewModel by viewModels()

    var oneTitle: String = ""
    var twoTitle: String = ""
    var threeTitle: String = ""
    var fourTitle: String = ""

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
        viewModel.fetchTrend()
        titleClick()
    }

    private fun initObserver() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }

    private fun initTrendObserver() {
        viewModel.trendItems.observe(viewLifecycleOwner) { titles ->
            if (titles.isNotEmpty()) {
                binding.tvHint.isVisible = true
                oneTitle = titles[0]
                twoTitle = titles[1]
                threeTitle = titles[2]
                fourTitle = titles[3]
                binding.tvFirst.text = oneTitle
                binding.tvSecond.text = twoTitle
                binding.tvThird.text = threeTitle
                binding.tvFour.text = fourTitle
            } else binding.tvHint.isVisible = false
        }
    }

    private fun initView() {
        with(binding) {
            val layoutManager = LinearLayoutManager(context)
            adapter = SearchAdapter(
                itemClickListener = { _, viewType, movie, _ ->
                    when (viewType) {
                        Constants.VIEW_TYPE_FAIL -> {
                            viewModel.fetch("")
                        }
                        else -> {
                            movie?.let {
                                val intent = Intent(context, PopularDetailActivity::class.java)
                                intent.putExtra(
                                    "movie_id",
                                    movie.id
                                )
                                startActivity(intent)
                            }
                        }
                    }
                })
            recyclerviewTheMore.adapter = adapter
            recyclerviewTheMore.layoutManager = layoutManager

            edtSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                    if (p1 == EditorInfo.IME_ACTION_DONE) {
                        val title: String = edtSearch.text.toString()
                        viewModel.fetch(title)
                        tvHint.isVisible = false
                        clearEdt()
                        return true
                    }
                    return false
                }
            })
            btnSearch.setOnClickListener {
                val title: String = edtSearch.text.toString()
                viewModel.fetch(title)
                tvHint.isVisible = false
                clearEdt()
                Log.d("kmh!!!", "버튼recyclerviewSize : ${adapter.itemCount}")
            }
            tvHint.bringToFront()
            animation()
            Log.d("kmh!!!", "recyclerviewSize : ${adapter.itemCount}")
        }
    }

    private fun titleClick() = with(binding) {
        tvFirst.setOnClickListener {
            edtSearch.setText(oneTitle)
        }
        tvSecond.setOnClickListener {
            edtSearch.setText(twoTitle)
        }
        tvThird.setOnClickListener {
            edtSearch.setText(threeTitle)
        }
        tvFour.setOnClickListener {
            edtSearch.setText(fourTitle)
        }
    }

    private fun clearEdt() = binding.edtSearch.setText("")

    private fun animation() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.alpha)
        binding.tvHint.startAnimation(animation)
    }

    companion object {
        fun newInstance() : Fragment {
            return SearchFragment()
        }
    }
}