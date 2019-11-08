package com.januszek.androidquizz2.profile


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.januszek.androidquizz2.QApp
import com.januszek.androidquizz2.news.NewsItem
import com.januszek.androidquizz2.news.NewsListFragment
import com.januszek.androidquizz2.news.NewsListRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import com.januszek.androidquizz2.R
import com.januszek.androidquizz2.toUserItem


class ProfileFragment : Fragment() {

    var currentUser: UserItem? = null

    var feedRef: Query? = null

    var respectsValue = 1

    val authListener:FirebaseAuth.AuthStateListener by lazy {
        FirebaseAuth.AuthStateListener { firebaseAuth ->
            if(firebaseAuth.currentUser != null){ //!=
                Log.d("NewsListFragment", "czy null?czy null?czy null?czy null?czy null?czy null?czy null?czy null?czy null?czy null?czy null?czy null?czy null?czy null?")
              updateCurrentUser()
 //               updateFeedRefEventListener()
               // loader_profil?.visibility = View.INVISIBLE
                updateLogon()
            } else {
                Log.d("NewsListFragment", "authListener - user:null")

            }
        }
    }

    private val mNewsMap: HashMap<String, NewsItem> = hashMapOf(
        // todo usunąć przy okazji internetu

        Pair(
            "adsf", NewsItem(
                comment = "komentarz",
                points = 10,
                quiz = "Kotlin łatwy",
                image = "https://cdn.stocksnap.io/img-thumbs/960w/6ZYX4YY4IR.jpg",
                user = "fions zdupy",
                timeMilis = System.currentTimeMillis() - 1000,
                uid = "sadfasf",
                respects = hashMapOf(Pair("sadfasf", 1), Pair("fsgf", 1))
            )
        ),
        Pair(
            "dup", NewsItem(
                comment = "komentarz dup",
                points = 10,
                quiz = "Kotlin łatwy",
                image = "https://cdn.stocksnap.io/img-thumbs/960w/6ZYX4YY4IR.jpg",
                user = "stefan zdupy",
                timeMilis = System.currentTimeMillis() - 432,
                uid = "sadfasf",
                respects = hashMapOf(Pair("sadfasf", 1))
            )
        ),
        Pair(
            "prot", NewsItem(
                comment = "komentarz",
                points = 10,
                quiz = "Kotlin łatwy",
                image = "https://cdn.stocksnap.io/img-thumbs/960w/6ZYX4YY4IR.jpg",
                user = "marian zdupy",
                timeMilis = System.currentTimeMillis() - 52,
                uid = "sadfasf",
                respects = hashMapOf(Pair("sadfasf", 1), Pair("fsgf", 1))

            )
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecycler()
    }

    private fun setUpRecycler() {
        if (context is NewsListFragment.OnNewsInteractionListener) {
            feed_recycler.layoutManager = LinearLayoutManager(context)
            feed_recycler.adapter = NewsListRecyclerViewAdapter(
                mNewsMap,
                context as NewsListFragment.OnNewsInteractionListener
            )
        }
    }

    override fun onStop() {
        super.onStop()
        QApp.fAuth.removeAuthStateListener(authListener)
        //usunac listenera listy newsow
    }

    override fun onStart() {
        super.onStart()
        QApp.fAuth.addAuthStateListener(authListener)
        updateCurrentUser()
        //updateFeedRefEventListener()
        updateLogon()
    }

    private fun updateCurrentUser() {
        System.out.println("updateCurrentUserupdateCurrentUserupdateCurrentUserupdateCurrentUserupdateCurrentUserupdateCurrentUserupdateCurrentUserupdateCurrentUserupdateCurrentUserupdateCurrentUserupdateCurrentUser")
        currentUser = arguments?.get(USER) as? UserItem
        if (currentUser == null) {
            val firebase = QApp.fAuth.currentUser
            firebase?.let {
                currentUser = firebase.toUserItem()

            }
        }
    }

    private fun updateLogon() {
        when {
            currentUser == null -> {
                setUpViewsNotLogged()
                sign_in_button.setOnClickListener {
                     (activity as OnLogChangeListener).onLogIn()
                    loader_profil.visibility = View.VISIBLE
                }
            }
            currentUser != null -> setUpViewsLogged()
        }
        // updateDebugFabLogout()
    }

    private fun setUpViewsNotLogged() {
        login_layout.visibility = View.VISIBLE
        feed_recycler.visibility = View.GONE
        respects.text = 0.toString()
        points.text = 0.toString()
        circleProfileImage.setImageDrawable(QApp.res.getDrawable(R.drawable.ic_anonym_face, null))
        collapsing_toolbar.title = QApp.res.getString(R.string.anonym_name)
    }

    private fun setUpViewsLogged() {
        login_layout.visibility = View.GONE
        feed_recycler.visibility = View.VISIBLE
        setUpUserData()
    }

    private fun setUpUserData() {
        collapsing_toolbar.title = currentUser!!.name
        Glide.with(this@ProfileFragment)
            .load(currentUser?.url)
            .into(circleProfileImage)
    }



    interface OnLogChangeListener {
        fun onLogout()
        fun onLogIn()
    }

    companion object {

        const val USER = "USER"
        /**
         *PL Używaj tej metody wytwórczej do tworzenia nowych instancji
         *PL jeśli zasilasz argumenty
         *EN Use this factory method to create a new instance of
         *EN this fragment using the provided parameters.
         */
        fun newInstance(user: UserItem): ProfileFragment {
            val frament = ProfileFragment()
            val bundle = Bundle()
            bundle.putSerializable(USER, user)
            frament.arguments = bundle
            return frament
        }
    }
}