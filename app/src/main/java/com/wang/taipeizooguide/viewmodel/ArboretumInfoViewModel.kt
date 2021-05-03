package com.wang.taipeizooguide.viewmodel

import com.wang.taipeizooguide.R

class ArboretumInfoViewModel : BaseViewModel() {
    val classPropertyNameMap = mapOf<String, Int>(
        "F_AlsoKnown" to R.string.text_alias,
        "F_Brief" to R.string.text_brief,
        "F_Feature" to R.string.text_feature,
        "F_Function_Application" to R.string.text_function_application
    )
}