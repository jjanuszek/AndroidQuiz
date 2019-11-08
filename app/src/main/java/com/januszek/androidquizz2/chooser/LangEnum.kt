package com.januszek.androidquizz2.chooser

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.januszek.androidquizz2.QApp
import com.januszek.androidquizz2.R

enum class LangEnum(@StringRes val label: Int,
                    @DrawableRes val image: Int) {
    ANDROID(R.string.lang_android, R.drawable.ic_language_android),
    KOTLIN(R.string.lang_kotlin, R.drawable.ic_language_kotlin),
    JAVA(R.string.lang_java, R.drawable.ic_language_java);
    fun getString() = QApp.res.getString(this.label)
}