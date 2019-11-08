package com.januszek.androidquizz2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.januszek.androidquizz2.chooser.QuizItem
import com.januszek.androidquizz2.chooser.QuizzChooserFragment
import com.januszek.androidquizz2.news.NewsListFragment
import com.januszek.androidquizz2.profile.OtherProfileActivity
import com.januszek.androidquizz2.profile.ProfileFragment
import com.januszek.androidquizz2.profile.UserItem
import com.januszek.androidquizz2.quiz.QuestionItem
import com.januszek.androidquizz2.quiz.QuizActivity
import com.januszek.androidquizz2.summary.QuizSummaryActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_newsitem_list.*
import kotlinx.android.synthetic.main.fragment_quizitem_list.*

class MainActivity : BaseActivity(),
    QuizzChooserFragment.OnStartQuizzListener,
    NewsListFragment.OnNewsInteractionListener,
    ProfileFragment.OnLogChangeListener{



    //todo interaction listenery
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViewPager()
    }

    //region Ustawienia viewPagera i bottomNavigatora
    private fun setViewPager() {
        viewpager.adapter = getFragmentPagerAdapter()
        navigation.setOnNavigationItemSelectedListener(getBottomNavigationItemSelectedListener())
        viewpager.addOnPageChangeListener(getOnPageChangeListener())
        viewpager
    }


    private fun getFragmentPagerAdapter() =
        object : FragmentPagerAdapter(
            supportFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) { //TODO BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            override fun getItem(position: Int) = when (position) {
                FEED_ID -> NewsListFragment()
                CHOOSER_ID -> QuizzChooserFragment()
                PROFILE_ID -> ProfileFragment()
                else -> {
                    Log.wtf("Fragment out of bounds", "How came?")
                    Fragment()
                }
            }

            override fun getCount() = 3


        }

    private fun getBottomNavigationItemSelectedListener() =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    viewpager.currentItem = 0
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    viewpager.currentItem = 1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    viewpager.currentItem = 2
                    return@OnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

    private fun getOnPageChangeListener() =
        object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                navigation.menu.getItem(position).isChecked = true
            }

        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when {
                (requestCode == QUIZ_ACT_REQ_CODE) -> {
                    navigateToSummaryActivity(data)
                }
                //(requestCode == QUIZ_SUMMARY_RCODE) -> {
                //    pushNewNews(data)
                //}
            }
        }
    }

    private fun navigateToSummaryActivity(data: Intent?) {
        val intent = Intent(this, QuizSummaryActivity::class.java).apply {
            if (QApp.fUser != null) {
                data?.putExtra(USER_NAME, "uzytkownik")
                data?.putExtra(USER_URL, "https://cdn.stocksnap.io/img-thumbs/960w/6ZYX4YY4IR.jpg")
            }
            putExtras(data!!.extras)
        }
        startActivityForResult(intent, QUIZ_SUMMARY_RCODE)
    }

    private fun getChooserListFragment() =
        (supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + CHOOSER_ID) as QuizzChooserFragment)
    private fun getNewsListFragment() =
        (supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + FEED_ID) as NewsListFragment)


    override fun onStartQuizzSelected(quiz: QuizItem, name: String) {
        getChooserListFragment().loader_quiz.visibility = View.VISIBLE
        //todo komunikacja
        var quizset = ArrayList<QuestionItem>().apply {
            add(QuestionItem())
            add(QuestionItem())
            add(QuestionItem())
            add(QuestionItem())
            add(QuestionItem())
        }
        navigateQuiz(quizset, name, quiz)
    }

    fun navigateQuiz(quizset: ArrayList<QuestionItem>, title: String, quiz: QuizItem) {
        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra(QUIZ_SET, quizset)
            putExtra(TITLE, title)
            putExtra(QUIZ, quiz)
        }
        startActivityForResult(intent, QUIZ_ACT_REQ_CODE)
    }
    override fun onUserSelected(user: UserItem, image: View) {
        val intent = Intent(this, OtherProfileActivity::class.java)
        intent.putExtra(USER_ITEM, user)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, image,"circleProfileImageTransition")
        startActivity(intent, optionsCompat.toBundle())
    }

    override fun onLikeSelected(feedId: String, diff: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLogout() {
        QApp.fAuth.signOut()
        getNewsListFragment().feed_item_list.adapter?.notifyDataSetChanged() //todo dodałem ? przed ntifyData..
    }



    override fun onLogIn() {
        logIn() // oddelegowanie do base activity
    }
    companion object {
        const val FEED_ID = 0
        const val CHOOSER_ID = 1
        const val PROFILE_ID = 2

        const val QUIZ_SET = "quiz_set"
        const val TITLE = "TITLE"
        const val QUIZ = "QUIZ"

        const val USER_ITEM = "USER_ITEM"


        const val USER_NAME = "USER_NAME"
        const val USER_URL = "USER_URL"
        const val QUIZ_ACT_REQ_CODE = 342 // liczba z dupy
        const val QUIZ_SUMMARY_RCODE = 115
//todo dlaczego mogę kożystać z tych stałych w quizactivity?
    }
}
