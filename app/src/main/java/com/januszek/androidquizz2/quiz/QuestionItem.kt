package com.januszek.androidquizz2.quiz

import java.io.Serializable

data class QuestionItem(
    var ask: String = "ask",
    var positive: String = "pos",
    var false1: String = "fals1",
    var false2: String = "fals2"): Serializable
