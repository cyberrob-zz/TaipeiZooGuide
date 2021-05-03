package com.wang.taipeizooguide.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.wang.taipeizooguide.R
import com.wang.taipeizooguide.util.spannable
import com.wang.taipeizooguide.util.underline
import kotlinx.android.synthetic.main.layout_title_description_container.view.*

class TitleDescriptionView(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.layout_title_description_container, this)
    }

    fun setup(title: String, description: String) {
        container_title?.text = spannable {
            underline(title)
        }

        container_description?.text = description
    }

}