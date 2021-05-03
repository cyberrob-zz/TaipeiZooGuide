package com.wang.taipeizooguide.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wang.taipeizooguide.R
import com.wang.taipeizooguide.viewmodel.ArboretumViewModel

class ArboretumListFragment : Fragment() {

    private lateinit var arboretumViewModel: ArboretumViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }
}