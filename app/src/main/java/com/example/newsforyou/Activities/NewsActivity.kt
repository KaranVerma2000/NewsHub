package com.example.newsforyou.Activities


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.newsforyou.R
import com.example.newsforyou.Utils.UtilMethods.convertISOTime
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NewsActivity : AppCompatActivity() {


    lateinit var image: ImageView
    lateinit var heading: TextView
    lateinit var description: TextView
    lateinit var newsContent: TextView
    lateinit var date: TextView
    lateinit var expandButton: FloatingActionButton
    lateinit var share: FloatingActionButton
    lateinit var bookmark: FloatingActionButton
    private var clicked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        image = findViewById(R.id.image)
        heading = findViewById(R.id.heading)
        description = findViewById(R.id.description)
        newsContent = findViewById(R.id.content)
        date = findViewById(R.id.date)
        expandButton = findViewById(R.id.expand)
        share = findViewById(R.id.share)
        bookmark = findViewById(R.id.bookmark)


        heading.text = intent.getStringExtra("title")


        if (intent.hasExtra("desc")) {
            try {
                description.text = intent.getStringExtra("desc")
            } catch (e: Exception) {
                description.text = ""
            }
        }

        if (intent.hasExtra("content")) {
            if (!intent.getStringExtra("content").isNullOrEmpty()) {
                if (intent.getStringExtra("content").toString().contains('['))
                    newsContent.text = intent.getStringExtra("content").toString()
                        .substringBeforeLast('[')
            } else
                newsContent.text = intent.getStringExtra("content")
        } else
            newsContent.text = ""

        date.text = convertISOTime(
            applicationContext,
            intent.getStringExtra("publishedAt")
        )


        Glide.with(this).load(intent.getStringExtra("urlToImage")).into(image)


        expandButton.setOnClickListener {
            onButtonClicked()
        }
        share.setOnClickListener {
            Toast.makeText(applicationContext,"clicked", Toast.LENGTH_SHORT).show()

            val imageUri = Uri.parse(intent.getStringExtra("urlToImage"))
            val sendIntent: Intent = Intent().setAction(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_TEXT, "*${intent.getStringExtra("title")}*\n\n" +
                    "${intent.getStringExtra("desc")}\n\n" +
                    "To read full news visit here :\n${intent.getStringExtra("url")}\n\n")
            sendIntent.type = "text/simple"


            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)


        }
        bookmark.setOnClickListener {
            Toast.makeText(applicationContext,"clicked", Toast.LENGTH_SHORT).show()
        }

    }

    private fun onButtonClicked() {
        if(!clicked){
            share.visibility = View.VISIBLE
            bookmark.visibility = View.VISIBLE
        }
        else{
            share.visibility = View.INVISIBLE
            bookmark.visibility = View.INVISIBLE
        }

        if (!clicked)
        {
            share.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.from_left))
            bookmark.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.from_bottom))
            expandButton.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_open))
        }
        else{
            share.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.to_left))
            bookmark.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.to_bottom))
            expandButton.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_close))
        }

        if (!clicked){
            share.isClickable = true
            bookmark.isClickable = true
        }
        else{
            share.isClickable = false
            bookmark.isClickable = false
        }


        clicked = !clicked
    }
}