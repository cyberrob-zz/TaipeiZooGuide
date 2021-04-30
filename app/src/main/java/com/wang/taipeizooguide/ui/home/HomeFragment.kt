package com.wang.taipeizooguide.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.orhanobut.logger.Logger
import com.wang.taipeizooguide.R
import com.wang.taipeizooguide.data.model.QueryParam
import com.wang.taipeizooguide.viewmodel.ExampleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val exampleViewModel: ExampleViewModel by viewModel()

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exampleViewModel.getZooList(queryParam = QueryParam(limit = 10))
            .observe(viewLifecycleOwner, {
                Logger.d(it)
            })

        exampleViewModel.getArboretumList(queryParam = QueryParam(limit = 5))
            .observe(viewLifecycleOwner, {
                Logger.d(it)
            })
    }
}