package org.techtown.diffuser.fragment.recommend.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.R

@AndroidEntryPoint
class BottomSheetFragment() : BottomSheetDialogFragment() {

    private lateinit var rv: RecyclerView
    private val viewModel: BottomSheetViewModel by viewModels()
    private lateinit var adapter: BottomSheetAdapter

    private lateinit var tvTitle: TextView
    private lateinit var tvStory: TextView
    private lateinit var btnClose: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_fragment, container, false)
        rv = view.findViewById(R.id.rv_middle)
        tvTitle = view.findViewById(R.id.tvBottomTitle)
        tvStory = view.findViewById(R.id.tvBottomStory)
        btnClose = view.findViewById(R.id.btnHide)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        viewModel.fetch()

        btnClose.setOnClickListener {
            dismiss()
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme).apply {
            behavior.isFitToContents = false
            behavior.peekHeight = 300
            behavior.expandedOffset = 150
            behavior.halfExpandedRatio = 0.65F
        }
        return dialog
    }

    private fun initView() {
        val layoutManager = GridLayoutManager(context, 5)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {

                if (position > 55) {
                    val size = 1
                    return size
                }
                val gridposition: Int = position % 19
                val size = if (gridposition == 1 || gridposition == 9 || gridposition == 12) {
                    3
                } else {
                    1
                }
                return size
            }
        }
        adapter = BottomSheetAdapter(
            itemClickListener = { _, viewType, movie, theMore ->
                tvTitle.text = movie?.title
                tvStory.text = movie?.overView
            }
        )

        rv.adapter = adapter
        rv.layoutManager = layoutManager
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItem = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (lastVisibleItem == totalItem - 1) {
                    viewModel.fetch()
                }
            }
        })
    }

    private fun initObserver() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }

}