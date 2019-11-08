package com.januszek.androidquizz2.chooser

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.januszek.androidquizz2.R
import kotlinx.android.synthetic.main.fragment_quizitem_list.*

/** Fragment eyboru quizu
 */

class QuizzChooserFragment  : Fragment(){


    private lateinit var onStartQuizListener: OnStartQuizzListener

    private val quizzesMap: HashMap<String, QuizItem> = HashMap()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnStartQuizzListener){
            onStartQuizListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_quizitem_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
        setCommunication()
    }

    private fun setCommunication() {
        quizzesMap.apply {
            put("23", QuizItem())
            put("243", QuizItem())
            put("223", QuizItem(lang = LangEnum.JAVA))
            put("213", QuizItem(level = LevelEnum.HARD))
            put("2431", QuizItem(level = LevelEnum.HARD))
        }
    }


    private fun setUpRecyclerView() {
        quest_item_list.layoutManager = GridLayoutManager(context, COLUMN_COUNT)
        //todo doimplementować w kolejnych lekcjach
        quest_item_list.adapter = QuizzChooserRecyclerViewAdapter(quizzesMap, onStartQuizListener)
    }

    interface OnStartQuizzListener{
        fun onStartQuizzSelected(quiz: QuizItem, string: String)
    }

companion object {
    private const val   COLUMN_COUNT = 2
}
}