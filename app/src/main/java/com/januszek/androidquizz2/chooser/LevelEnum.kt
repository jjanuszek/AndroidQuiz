package com.januszek.androidquizz2.chooser

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.januszek.androidquizz2.QApp
import com.januszek.androidquizz2.R

enum class LevelEnum(@StringRes val label: Int,
                     @DrawableRes val image: Int) {
    EASY(R.string.level_easy, R.drawable.ic_level_easy),
    AVERAGE(R.string.level_average, R.drawable.ic_level_average),
    HARD(R.string.level_hard, R.drawable.ic_level_hard);
    fun getString() = QApp.res.getString(this.label)
}