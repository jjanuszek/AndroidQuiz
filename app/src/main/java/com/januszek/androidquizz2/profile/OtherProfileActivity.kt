package com.januszek.androidquizz2.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.januszek.androidquizz2.BaseActivity
import com.januszek.androidquizz2.MainActivity
import com.januszek.androidquizz2.QApp
import com.januszek.androidquizz2.R
import com.januszek.androidquizz2.news.NewsListFragment
import kotlinx.android.synthetic.main.fragment_profile.*

//36
class OtherProfileActivity : BaseActivity(),
    NewsListFragment.OnNewsInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = intent?.extras?.get(MainActivity.USER_ITEM) as UserItem
        setContentView(R.layout.other_profile_activity)
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.layout_other_profile, ProfileFragment.newInstance(user)).commit()
    }

    override fun onStart() {
        super.onStart()
        setUpToolbar()
    }

    /**
     * Dostosowuje fragment profili pod obcy profil. Prawdopodobnie do refaktoru, ale jak się wyklaruje okno
     * Nie bierz tej metody na poważnie, we fragmencie narobiłoby niezłego szamba
     * a wystawianie kolejnych interfejsów tam sobie darujmy
     */
    private fun setUpToolbar(){// androidx.appcompat:appcompat:1.1.0   android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onUserSelected(user: UserItem, image: View) {
        //niedostepne na tym oknie
    }

    override fun onLikeSelected(feedId: String, diff: Int) {
       // if (QApp.fUser != null) {
       //     QApp.fData.getReference("feeds/$feedId/respects").updateChildren(mapOf(Pair(QApp.fUser?.uid, diff)))
       //         .addOnCompleteListener { Log.d("MainActivity", "Just liked $feedId, with $diff") }
       // }
    }
}