package com.wang.taipeizooguide.ui

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.orhanobut.logger.Logger
import com.wang.taipeizooguide.R
import com.wang.taipeizooguide.data.model.Zoo
import com.wang.taipeizooguide.viewmodel.AttractionInfoViewModel
import kotlinx.android.synthetic.main.fragment_attraction_info.*
import kotlinx.android.synthetic.main.fragment_zoo_list.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class AttractionInfoFragment : Fragment() {

    private val viewModel: AttractionInfoViewModel by viewModel()

    private lateinit var backPressedCallback: OnBackPressedCallback

    private lateinit var arboretumAdapter: ArboretumListAdapter

    companion object {
        const val BUNDLE_ATTRACTION = "BUNDLE_ATTRACTION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_attraction_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleDataFromArgument()
        setupBackPressCallback()
        setupView()

        lifecycleScope.launch {
            viewModel.arboretumList.collect {
                if (::arboretumAdapter.isInitialized) {
                    arboretumAdapter.submitData(it)
                }
            }
        }
    }

    private fun setupView() {
        viewModel.targetZooAttraction?.let {
            (activity as AppCompatActivity).supportActionBar?.title = it.E_Name
            (activity as AppCompatActivity).supportActionBar?.subtitle = it.E_Category

            Glide.with(attraction_image)
                .load(it.E_Pic_URL)
                .apply(RequestOptions().centerCrop())
                .into(attraction_image)

            attraction_info.text = it.E_Info
        }

        arboretumAdapter = ArboretumListAdapter().apply {
            arboretumClickListener = getArboretumClickListener()
            addLoadStateListener { loadState ->
                when (loadState.refresh) {
                    is LoadState.NotLoading -> {
                        Logger.d("Not Loading...")
                        if (this.itemCount == 0) {
                            arboretum_placeholder_view.visibility = View.VISIBLE
                            on_site_arboretum_list.visibility = View.GONE
                            arboretum_placeholder_view.text = getString(R.string.no_data)
                        } else {
                            arboretum_placeholder_view.visibility = View.GONE
                            on_site_arboretum_list.visibility = View.VISIBLE
                        }
                    }
                    LoadState.Loading -> {
                        Logger.d("Loading...")
                        arboretum_placeholder_view.visibility = View.VISIBLE
                        on_site_arboretum_list.visibility = View.GONE
                        arboretum_placeholder_view.text = getString(R.string.text_loading)
                    }
                    is LoadState.Error -> {
                        Logger.e("Error occurred: ${(loadState.refresh as LoadState.Error).error}")
                        arboretum_placeholder_view.visibility = View.VISIBLE
                        on_site_arboretum_list.visibility = View.GONE
                        arboretum_placeholder_view.text = getString(R.string.text_error_occurred)
                    }
                }
            }
        }
        val linearLayoutManager = LinearLayoutManager(context)
        on_site_arboretum_list.apply {
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

        Logger.d("on_site_arboretum_list visible:　${on_site_arboretum_list.isVisible}")
        Logger.d("on_site_arboretum_list height: ${on_site_arboretum_list.measuredHeight}")
    }

    private fun getArboretumClickListener(): ArboretumClickListener = { araboretum ->
        view?.run safeView@{
//            Navigation.findNavController(this@safeView)
//                .navigate(
//                    R.id.action_navigation_zoo_to_attraction_page, bundleOf(
//                        BUNDLE_ATTRACTION to zoo
//                    )
//                )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        backPressedCallback.isEnabled = false
        backPressedCallback.remove()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.attraction_info_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.navigation_attraction_link) {
            viewModel.targetZooAttraction?.run {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(this.E_URL))
                )
            }
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupBackPressCallback() {
        backPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController(this@AttractionInfoFragment).navigateUp()
        }
    }

    private fun getBundleDataFromArgument() {
        requireArguments().run {
            getParcelable<Zoo>(BUNDLE_ATTRACTION)?.let {
                viewModel.targetZooAttraction = it
            }
        }
    }

}