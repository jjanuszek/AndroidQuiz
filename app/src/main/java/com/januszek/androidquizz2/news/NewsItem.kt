package com.januszek.androidquizz2.news

import java.io.Serializable


data class NewsItem(
    var comment: String = "",
    var points: Int = 0,
    var quiz: String="",
    var image: String="",
    var user: String="",
    var timeMilis: Long=0,
    var uid: String = "",
    var respects: HashMap<String, Int> = hashMapOf()): Serializable
