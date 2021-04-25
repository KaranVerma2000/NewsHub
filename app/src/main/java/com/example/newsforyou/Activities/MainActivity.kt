package com.example.newsforyou.Activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import com.example.newsforyou.Fragments.NewsFragment
import com.example.newsforyou.Fragments.ProfileFragment
import com.example.newsforyou.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var newsFragment = NewsFragment()
    private var profileFragment = ProfileFragment()
    private lateinit var bottomNav : BottomNavigationView
    lateinit var motion_layout : DrawerLayout
    lateinit var entertainment : TextView
    lateinit var technology : TextView
    lateinit var international : TextView
    lateinit var business : TextView
    lateinit var medical : TextView
    lateinit var sports : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        entertainment = findViewById(R.id.Entertainment)
        sports = findViewById(R.id.Sports)
        business = findViewById(R.id.Business)
        medical = findViewById(R.id.Medical)
        technology = findViewById(R.id.Technology)
        international = findViewById(R.id.International)
        motion_layout = findViewById(R.id.motionLayout)
        bottomNav = findViewById(R.id.bottom_nav)


            //Log.d("name", intent.getStringExtra("name").toString())


        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.profile ->
                {
                    displayProfile()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.news ->
                {
                    displayNews()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
        bottomNav.selectedItemId = R.id.news
        supportFragmentManager.beginTransaction().replace(R.id.frame, newsFragment).commit()


    }

    private fun displayProfile() {
        if (supportFragmentManager.findFragmentById(R.id.frame) is ProfileFragment)
            return
        supportFragmentManager.beginTransaction().replace(R.id.frame, profileFragment).commit()
    }

    private fun displayNews() {
        if (supportFragmentManager.findFragmentById(R.id.frame) is NewsFragment)
            return
        supportFragmentManager.beginTransaction().replace(R.id.frame, newsFragment).commit()
    }
}