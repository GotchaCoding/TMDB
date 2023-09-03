package org.techtown.diffuser.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    private var _binding: T? = null
    val binding: T
        get() = _binding!!

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("kmh!!!", "onCreateView")
        _binding = bindingInflater(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return _binding!!.root
    }

    override fun onDestroyView() { //프래그먼트는 라이프사이클이 view 보다 오래 지속되기때문에 메모리 누수 방지를 위해 onDestroyView 에서 생명주기를 관리해주어야 함.
        _binding = null   // _binding  변수를 만들어서 직접 해제하여 메모리 누수를 대응함.
        super.onDestroyView()
    }

}
