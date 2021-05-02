package com.wang.taipeizooguide.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.wang.taipeizooguide.R
import com.wang.taipeizooguide.viewmodel.ZooViewModel
import kotlinx.android.synthetic.main.fragment_zoo_list.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ZooListFragment : Fragment() {

    private val zooViewModel: ZooViewModel by viewModel()

    private lateinit var zooListAdapter: ZooListAdapter


    private fun setupView() {
        zooListAdapter = ZooListAdapter().apply {
            zooClickListener = getZooClickListener()
            addLoadStateListener { loadState ->
                when (loadState.refresh) {
                    is LoadState.NotLoading -> {
                        if (swipeRefreshLayout.isRefreshing) {
                            swipeRefreshLayout.isRefreshing = false
                        }
                    }
                    LoadState.Loading -> {
                        Logger.d("Loading...")
                    }
                    is LoadState.Error -> {
                        Logger.e("Error occurred: ${loadState.refresh.javaClass}")
                    }
                }
            }
        }
        swipeRefreshLayout.setOnRefreshListener { zooListAdapter.refresh() }
        val linearLayoutManager = LinearLayoutManager(context)
        zoo_recycler_view.apply {
            layoutManager = linearLayoutManager
            if (::zooListAdapter.isInitialized) {
                adapter = zooListAdapter
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

    private fun getZooClickListener(): ZooClickListener = { zoo ->
        Logger.d("Clicked zoo: $zoo")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_zoo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()

        lifecycleScope.launch {
            zooViewModel.zooList.collect {
                if (::zooListAdapter.isInitialized) {
                    zooListAdapter.submitData(it)
                }
            }
        }

//        exampleViewModel.getZooList(queryParam = QueryParam(limit = 10))
//            .observe(viewLifecycleOwner, {
//                Logger.d(it)
//            })
//
//        exampleViewModel.getArboretumList(queryParam = QueryParam(limit = 5))
//            .observe(viewLifecycleOwner, {
//                Logger.d(it)
//            })
    }
}