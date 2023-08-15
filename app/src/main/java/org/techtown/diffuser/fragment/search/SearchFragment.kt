package org.techtown.diffuser.fragment.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.R
import org.techtown.diffuser.activity.detailpage.PopularDetailActivity
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.databinding.FragmentSearchBinding
import org.techtown.diffuser.fragment.BaseFragment
import org.techtown.diffuser.fragment.ItemClickListener
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.Movie

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var adapter: SearchAdapter

    private var oneTitle: String = ""
    private var twoTitle: String = ""
    private var threeTitle: String = ""
    private var fourTitle: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        viewModel.fetchTrend()
    }

    private fun initView() = with(binding) {
        val layoutManager = LinearLayoutManager(context)
        adapter = SearchAdapter(
            itemClickListener = object : ItemClickListener {
                override fun onItemClick(
                    view: View,
                    viewType: Int,
                    movie: Movie?,
                    theMore: TheMore?
                ) {
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
        }
        tvHint.bringToFront()
        animation()
        Log.d("kmh!!!", "recyclerviewSize : ${adapter.itemCount}")

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

    private fun initObserver() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)

            if (items.isNotEmpty()) {
                hideKeyboard()
            } else {
                toastMessage()
            }
        }

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

    private fun animation() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.alpha)
        binding.tvHint.startAnimation(animation)
    }

    private fun hideKeyboard() {
        if (activity != null && requireActivity().currentFocus != null) {
            val inputManager: InputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                requireActivity().currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    private fun toastMessage() {
        val message: String = "검색 결과가 없습니다."
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): Fragment {
            return SearchFragment()
        }
    }
}