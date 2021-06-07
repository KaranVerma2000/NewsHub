package com.example.newsforyou.Activities


import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.newsforyou.Fragments.NewsFragment
import com.example.newsforyou.Fragments.ProfileFragment
import com.example.newsforyou.R
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape
import java.util.*

class MainActivity : AppCompatActivity() {

    private var newsFragment = NewsFragment()
    private var profileFragment = ProfileFragment()
    private lateinit var bottomNav: ExpandableBottomBar
    lateinit var motion_layout: DrawerLayout
    lateinit var entertainment: TextView
    lateinit var technology: TextView
    lateinit var international: TextView
    lateinit var business: TextView
    lateinit var medical: TextView
    lateinit var sports: TextView
    lateinit var bookmarkTab: TextView

    var fancyHome: FancyShowCaseView? = null
    var fancyProfile: FancyShowCaseView? = null

    var fancyShowCaseQueue: FancyShowCaseQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initViews()

        onShowIntro()

        val menu = bottomNav.menu
        menu.select(R.id.news)
        displayNews()

        bottomNav.onItemSelectedListener = { view, menuItem, bool ->
            when (menuItem.id) {
                R.id.profile -> {
                    displayProfile()
                }
                R.id.news -> {
                    displayNews()
                }
            }
        }

//        bottomNav.selectedItemId = R.id.news
//        supportFragmentManager.beginTransaction().replace(R.id.frame, newsFragment).commit()


    }

    private fun onShowIntro() {



        val typeface =
            ResourcesCompat.getFont(this, R.font.prociono)


        fancyHome = FancyShowCaseView.Builder(this@MainActivity)
            .focusOn(Objects.requireNonNull(bottomNav.getChildAt(0)))
            .focusShape(FocusShape.ROUNDED_RECTANGLE)
            .roundRectRadius(90)
            .titleGravity(Gravity.CENTER)
            .titleSize(28, TypedValue.COMPLEX_UNIT_SP)
            .enableAutoTextPosition()
            .typeface(typeface)
            .showOnce("FANCY_HOME")
            .title("Global News")
            .build()

        fancyProfile = FancyShowCaseView.Builder(this@MainActivity)
            .focusOn(Objects.requireNonNull(bottomNav.getChildAt(1)))
            .focusShape(FocusShape.ROUNDED_RECTANGLE)
            .roundRectRadius(90)
            .titleGravity(Gravity.CENTER)
            .titleSize(28, TypedValue.COMPLEX_UNIT_SP)
            .enableAutoTextPosition()
            .typeface(typeface)
            .showOnce("FANCY_PROFILE")
            .title("Tap to see your Profile")
            .build()

        fancyShowCaseQueue = FancyShowCaseQueue()
        fancyShowCaseQueue!!.add(fancyHome!!).add(fancyProfile!!)

        fancyShowCaseQueue!!.show()
    }

    private fun initViews() {
        entertainment = findViewById(R.id.Entertainment)
        sports = findViewById(R.id.Sports)
        business = findViewById(R.id.Business)
        medical = findViewById(R.id.Medical)
        technology = findViewById(R.id.Technology)
        international = findViewById(R.id.International)
        bookmarkTab = findViewById(R.id.Bookmark)
        motion_layout = findViewById(R.id.motionLayout)
        bottomNav = findViewById(R.id.bottom_nav)
    }

    private fun displayProfile() {
        if (supportFragmentManager.findFragmentById(R.id.frame) is ProfileFragment)
            return

        supportFragmentManager.beginTransaction().addToBackStack(null)
            .replace(R.id.frame, profileFragment).commit()
    }

    private fun displayNews() {
        if (supportFragmentManager.findFragmentById(R.id.frame) is NewsFragment)
            return
        supportFragmentManager.beginTransaction().replace(R.id.frame, newsFragment).commit()
    }
}