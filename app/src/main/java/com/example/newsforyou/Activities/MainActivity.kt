package com.example.newsforyou.Activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        motion_layout = findViewById(R.id.motionLayout)
        bottomNav = findViewById(R.id.bottom_nav)
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