package org.techtown.diffuser.fragment.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.techtown.diffuser.R
import org.techtown.diffuser.databinding.ActivityHomeFragmentBinding
import org.techtown.diffuser.databinding.ActivitySearchFragmentBinding

class SearchFragment : Fragment() {
    lateinit var binding : ActivitySearchFragmentBinding

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


    }
}