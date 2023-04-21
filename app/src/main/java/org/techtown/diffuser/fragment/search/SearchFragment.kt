package org.techtown.diffuser.fragment.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.R
import org.techtown.diffuser.databinding.ActivityHomeFragmentBinding
import org.techtown.diffuser.databinding.ActivitySearchFragmentBinding
import org.techtown.diffuser.fragment.home.HomeAdapter

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var binding : ActivitySearchFragmentBinding
//    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivitySearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        var editText = binding.btnSearch
//        editText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
//            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
//                if(p1 == EditorInfo.IME_ACTION_DONE) {
//                    return false
//                }
//            }
//            return false
//
//
//        })
//        initView()
//
//
//    }
//
//    private fun initView() {
//
//    }
}