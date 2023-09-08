package org.techtown.diffuser.fragment.search

import android.content.Context
import android.os.Bundle
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
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.R
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.databinding.FragmentSearchBinding
import org.techtown.diffuser.fragment.BaseFragment
import org.techtown.diffuser.fragment.ItemClickListener
import org.techtown.diffuser.fragment.home.HomeFragmentDirections
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
                                findNavController().navigate(HomeFragmentDirections.actionHomeToDetail(movie.id))
                            }
                        }
                    }
                }
            })
        recyclerviewTheMore.adapter = adapter
        recyclerviewTheMore.layoutManager = LinearLayoutManager(context)

        edtSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_DONE) {
                    onSearch(edtSearch.text.toString())
                    edtSearch.text.clear()
                    return true
                }
                return false
            }
        })

        edtSearch. addTextChangedListener { editable ->
            viewModel.onSearch(editable.toString())
        }

        btnSearch.setOnClickListener {
            onSearch(edtSearch.text.toString())
            edtSearch.text.clear()
        }

        tvHint.bringToFront()
        animation()

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

    private fun onSearch(keyword: String) {
        viewModel.onSearch(keyword)
        hideKeyboard()
    }


    private fun initObserver() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

        viewModel.toast.observe(viewLifecycleOwner) {
            toastMessage(it)
        }

        viewModel.isHintVisible.observe(viewLifecycleOwner) {
            binding.tvHint.isVisible = it
        }

        viewModel.trendItems.observe(viewLifecycleOwner) { titles ->
            if (titles.isNotEmpty()) {
                oneTitle = titles[0]
                twoTitle = titles[1]
                threeTitle = titles[2]
                fourTitle = titles[3]
                binding.tvFirst.text = oneTitle
                binding.tvSecond.text = twoTitle
                binding.tvThird.text = threeTitle
                binding.tvFour.text = fourTitle
            }
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

    private fun toastMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}