package com.wang.taipeizooguide.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.wang.taipeizooguide.R
import com.wang.taipeizooguide.viewmodel.AboutViewModel
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.core.MarkwonTheme
import kotlinx.android.synthetic.main.fragment_about.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class AboutFragment : Fragment() {

    private val aboutViewModel: AboutViewModel by viewModel()

    private lateinit var markown: Markwon

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_about, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        markown = Markwon.builder(requireContext())
//            .usePlugin(ImagesPlugin.create())
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configureTheme(builder: MarkwonTheme.Builder) {
                    builder
                        .codeTextColor(Color.BLACK)
                        .linkColor(ContextCompat.getColor(requireContext(), R.color.green_600))
                        .codeBackgroundColor(Color.GREEN)
                }
            })
            .build()



        aboutViewModel.aboutText.observe(viewLifecycleOwner, {
            text_about?.run {
                markown.setMarkdown(this, it)
            }
        })
    }
}