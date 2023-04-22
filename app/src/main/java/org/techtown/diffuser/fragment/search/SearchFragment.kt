package org.techtown.diffuser.fragment.search

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.databinding.ActivitySearchFragmentBinding
import org.techtown.diffuser.fragment.home.HomeAdapter
import org.techtown.diffuser.fragment.home.HomeViewModel
import org.w3c.dom.Text

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var binding: ActivitySearchFragmentBinding

    private lateinit var adapter: SearchAdapter

    private val viewModel: SearchViewModel by viewModels()

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

        var editText = binding.btnSearch
        var text: String = editText.text.toString()



//        editText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
//            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
//                if (p1 == EditorInfo.IME_ACTION_DONE) {
//                    var text = editText.text.toString()
////                    viewModel.fetch(text)
//                    Toast.makeText(context, "Welcome to GFG", Toast.LENGTH_SHORT).show()
//                    return true
//                }
//                return false
//            }

//        })
        initView()
        initObserver()
        viewModel.fetch()

    }

    private fun initObserver() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }

    private fun initView() {
        with(binding) {
            val layoutManager = LinearLayoutManager(context)
            adapter = SearchAdapter()
            recyclerviewTheMore.adapter = adapter
            recyclerviewTheMore.layoutManager = layoutManager
        }
    }
}