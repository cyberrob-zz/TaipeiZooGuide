package com.wang.taipeizooguide.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.wang.taipeizooguide.R
import com.wang.taipeizooguide.ui.adapter.ArboretumListAdapter
import com.wang.taipeizooguide.viewmodel.ArboretumViewModel
import kotlinx.android.synthetic.main.fragment_arboretum_list.*
import kotlinx.android.synthetic.main.fragment_zoo_list.*
import kotlinx.android.synthetic.main.fragment_zoo_list.swipeRefreshLayout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ArboretumListFragment : Fragment() {

    private val arboretumViewModel: ArboretumViewModel by viewModel()

    private lateinit var arboretumAdapter: ArboretumListAdapter

    private var dialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_arboretum_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()

        refreshArboretumAPI()
    }

    override fun onDestroyView() {
        dialog = null
        super.onDestroyView()
    }

    private fun refreshArboretumAPI() {
        lifecycleScope.launch {
            arboretumViewModel.arboretumList.collect {
                if (::arboretumAdapter.isInitialized) {
                    arboretumAdapter.submitData(it)
                }
            }
        }
    }

    private fun setupView() {
        arboretumAdapter = ArboretumListAdapter().apply {
            //zooClickListener = getZooClickListener()
            addLoadStateListener { loadState ->
                when (loadState.refresh) {
                    is LoadState.NotLoading -> {
                        if (swipeRefreshLayout?.isRefreshing == true) {
                            swipeRefreshLayout?.isRefreshing = false
                        }
                    }
                    LoadState.Loading -> {
                        Logger.d("Loading...")
                        if (swipeRefreshLayout?.isRefreshing == false) {
                            swipeRefreshLayout?.isRefreshing = true
                        }
                    }
                    is LoadState.Error -> {
                        Logger.e("Error occurred: ${loadState.refresh.javaClass}")
                        dialog = AlertDialog.Builder(requireContext())
                            .setTitle(R.string.dialog_error_title)
                            .setMessage(R.string.dialog_error_message)
                            .setPositiveButton(
                                R.string.text_ok
                            ) { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
            }
        }
        swipeRefreshLayout?.setOnRefreshListener { arboretumAdapter.refresh() }
        val linearLayoutManager = LinearLayoutManager(context)
        arboretum_recycler_view?.apply {
            layoutManager = linearLayoutManager
            if (::arboretumAdapter.isInitialized) {
                adapter = arboretumAdapter
            }

            with(object :
                DividerItemDecoration(context, linearLayoutManager.orientation) {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val position = parent.getChildAdapterPosition(view)
                    if (position == state.itemCount - 1) {
                        outRect.setEmpty()
                    } else {
                        super.getItemOffsets(outRect, view, parent, state)
                    }
                }
            }) dividerDecorator@{
                ContextCompat.getDrawable(context, R.drawable.layout_recyclerview_divider)?.let {
                    this.setDrawable(
                        it
                    )
                }
                this@apply.addItemDecoration(this@dividerDecorator)
            }
        }
    }
}