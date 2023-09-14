package org.techtown.diffuser.fragment.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.R
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.constants.Constants.KEY_RECYCLERVIEW_ID_WORD_RECORD
import org.techtown.diffuser.constants.Constants.VIEW_TYPE_WORD_RECORD
import org.techtown.diffuser.databinding.FragmentSearchBinding
import org.techtown.diffuser.fragment.BaseFragment
import org.techtown.diffuser.fragment.ItemClickListener
import org.techtown.diffuser.fragment.ItemClickListenerWord
import org.techtown.diffuser.fragment.home.HomeFragmentDirections
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.room.Word

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var adapter: SearchAdapter
    private lateinit var adapterWord: WordAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
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
                                findNavController().navigate(
                                    HomeFragmentDirections.actionHomeToDetail(
                                        movie.id
                                    )
                                )
                            }
                        }
                    }
                }
            })
        recyclerviewTheMore.adapter = adapter
        recyclerviewTheMore.layoutManager = LinearLayoutManager(context)


        adapterWord = WordAdapter(itemClickListener = object : ItemClickListenerWord {
            override fun onItemClickWord(view: View, word: Word) {
                when (view.id) {
                    R.id.btnCancle -> {
                        Log.e("kmh", "btnCancle click")
                        viewModel.deleteSelectedWord(word)
                    }

                    R.id.tvWord -> {
                        Log.e("kmh", "tvWord click")
                        edtSearch.setText(word.word)
                    }

                }
            }

        })
        recyclerviewWord.adapter = adapterWord
        recyclerviewWord.layoutManager = LinearLayoutManager(context)
        recyclerviewWord.bringToFront()
        recyclerviewWord.visibility = View.GONE


        edtSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_DONE) {
                    viewModel.onSearch(edtSearch.text.toString())
                    return true
                }
                return false
            }
        })

        edtSearch.addTextChangedListener { editable ->
            viewModel.onSearch(editable.toString())
        }


        btnSearch.setOnClickListener {
            /*
            * if(editText.isNotEmpty()){
            *   save the word to database
            * }
            *
            * search keyword by tmdb api
            * search keyword clear
            * search focus clear
            *
            * viewModel.onSearch()
            *
            *
            * */

            if (edtSearch.text.toString().isNotEmpty()) {
                val word = Word(
                    word = edtSearch.text.toString(),
                    viewType = VIEW_TYPE_WORD_RECORD,
                    id = KEY_RECYCLERVIEW_ID_WORD_RECORD
                )
                viewModel.insertWord(word)
            }
            onSearch(edtSearch.text.toString())
            edtSearch.text.clear()
            edtSearch.clearFocus()
        }

        edtSearch.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                recyclerviewWord.visibility = View.GONE
            } else {
                recyclerviewWord.visibility = View.VISIBLE
            }
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

        viewModel.recentWords.observe(viewLifecycleOwner) {
            adapterWord.submitList(it)
        }

        viewModel.clearKeywordEvent.observe(viewLifecycleOwner) {
            binding.edtSearch.text.clear()
            binding.edtSearch.clearFocus()
        }

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