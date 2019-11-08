package com.januszek.androidquizz2.chooser

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.januszek.androidquizz2.R

/**--------16---------
 *
 */

class QuizzChooserRecyclerViewAdapter(private val quizzesMap: HashMap<String, QuizItem>,
                                      private  val onStartquizListener:QuizzChooserFragment.OnStartQuizzListener): RecyclerView.Adapter<QuizzChooserRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_quizitem,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()=quizzesMap.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val sorted = quizzesMap.values.toList().sortedBy { quizItem -> (quizItem.level.ordinal + quizItem.lang.ordinal * 10) }
        holder.mItem = sorted[position]

        holder.quizTitlleView.setImageResource(sorted[position].level.image)
        holder.quizTitllView.setImageResource(sorted[position].lang.image)
        holder.equizTitle.text =getDoubleLineQuizTitle(sorted,position)

        holder.mView.setOnClickListener{
            (onStartquizListener.onStartQuizzSelected(holder.mItem,getSingleLineQuizzTitle(sorted, position)))}
    }

    private fun getSingleLineQuizzTitle(sorted: List<QuizItem>, position: Int)
            ="${sorted[position].lang.getString()} \n ${sorted[position].level.getString()}"


    private fun getDoubleLineQuizTitle(sorted: List<QuizItem>, position: Int)
            ="${sorted[position].lang.getString()} \n ${sorted[position].level.getString()}"

    inner class  ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView){
        val quizTitlleView = mView.findViewById<View>(R.id.levelImageView) as ImageView
        val quizTitllView = mView.findViewById<View>(R.id.langImageView) as ImageView
        val equizTitle = mView.findViewById<View>(R.id.quizTitle) as TextView

        lateinit var mItem: QuizItem

    }
}