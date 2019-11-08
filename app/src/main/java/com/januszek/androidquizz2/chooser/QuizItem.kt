package com.januszek.androidquizz2.chooser

import java.io.Serializable

data class QuizItem(
        var level: LevelEnum = LevelEnum.EASY,
        var lang: LangEnum = LangEnum.ANDROID,
        var guestset: String = "") : Serializable


