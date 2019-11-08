package com.januszek.androidquizz2.news


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.januszek.androidquizz2.QApp
import com.januszek.androidquizz2.R
import com.januszek.androidquizz2.profile.UserItem
import kotlinx.android.synthetic.main.fragment_newsitem.*
import kotlinx.android.synthetic.main.fragment_newsitem_list.*

class NewsListFragment : Fragment() {

    private lateinit var onNewsInteractionListener: OnNewsInteractionListener

    private val mNewsMap:HashMap<String, NewsItem> = hashMapOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNewsInteractionListener){
            onNewsInteractionListener = context
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_newsitem_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loader_news.visibility = View.VISIBLE

        //feedsRef.addListenerForSingleValueEvent(object : ValueEventListener {
        //    override fun onCancelled(p0: DatabaseError) {}
//
        //    override fun onDataChange(p0: DataSnapshot) {
        //        loader_news.visibility = View.GONE
        //        onUpdateRecyclerAdapter(p0)
        //        feed_item_list.scheduleLayoutAnimation()
        //    }
        //})

        mNewsMap.apply {
            put("adsf", NewsItem(
                comment = "komentarz",
                points= 10,
                quiz = "Kotlin łatwy",
                image = "https://cdn.stocksnap.io/img-thumbs/960w/6ZYX4YY4IR.jpg",
                user = "fions zdupy",
                timeMilis = System.currentTimeMillis()-1000,
                uid = "sadfasf",
                respects = hashMapOf(Pair("sadfasf",1), Pair("fsgf",1))

            ))
        }
        mNewsMap.apply {
            put("dup", NewsItem(
                comment = "komentarz dup",
                points= 10,
                quiz = "Kotlin łatwy",
                image = "https://cdn.stocksnap.io/img-thumbs/960w/6ZYX4YY4IR.jpg",
                user = "stefan zdupy",
                timeMilis = System.currentTimeMillis()-432,
                uid = "sadfasf",
                respects = hashMapOf(Pair("sadfasf",1))

            ))
        }
        mNewsMap.apply {
            put("prot", NewsItem(
                comment = "komentarz",
                points= 10,
                quiz = "Kotlin łatwy",
                image = "https://cdn.stocksnap.io/img-thumbs/960w/6ZYX4YY4IR.jpg",
                user = "marian zdupy",
                timeMilis = System.currentTimeMillis()-52,
                uid = "sadfasf",
                respects = hashMapOf(Pair("sadfasf",1), Pair("fsgf",1))

            ))
        }
        feed_item_list.scheduleLayoutAnimation()
        loader_news.visibility = View.GONE
        setUpRecycler()
    }

    private fun setUpRecycler() {
        feed_item_list.layoutManager = LinearLayoutManager(context)
        feed_item_list.adapter = NewsListRecyclerViewAdapter(mNewsMap, onNewsInteractionListener)
    }

    interface OnNewsInteractionListener {
        fun onUserSelected(user: UserItem, image: View)
        fun onLikeSelected(feedId: String, diff: Int)
    }
}