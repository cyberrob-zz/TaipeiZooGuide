package com.wang.taipeizooguide.viewmodel

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.wang.taipeizooguide.R
import java.io.InputStream


class AboutViewModel(context: Context) : BaseViewModel() {

    private val _aboutText = MutableLiveData<String>().apply {
        value =
            try {
                val res: Resources = context.resources
                val inputStream: InputStream = res.openRawResource(R.raw.about)
                val b = ByteArray(inputStream.available())
                inputStream.read(b)
                String(b)
            } catch (e: Exception) {
                Logger.e(e.toString())
                ""
            }
    }
    val aboutText: LiveData<String> = _aboutText

}