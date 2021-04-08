package com.example.newsforyou.Activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsforyou.Fragments.NewsFragment
import com.example.newsforyou.Fragments.ProfileFragment
import com.example.newsforyou.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var newsFragment = NewsFragment()
    private var profileFragment = ProfileFragment()
    private lateinit var bottomNav : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.setOnNavigationItemSelectedListener {
            if (it.itemId == R.id.news) {
                displayNews()
            }
            else if (it.itemId == R.id.profile) {
                displayProfile()
            }
            return@setOnNavigationItemSelectedListener true
        }


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