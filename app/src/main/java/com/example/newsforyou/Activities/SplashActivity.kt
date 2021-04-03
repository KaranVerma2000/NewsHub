package com.example.newsforyou.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.newsforyou.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val image = findViewById<ImageView>(R.id.image)
        val text2 = findViewById<TextView>(R.id.newsText2)



        image.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
        text2.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.bounce))


        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        }, 2500)
    }
}