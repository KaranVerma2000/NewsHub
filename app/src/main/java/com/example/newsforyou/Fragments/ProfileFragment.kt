package com.example.newsforyou.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.newsforyou.Activities.LoginActivity
import com.example.newsforyou.Activities.MainActivity
import com.example.newsforyou.R
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var LogOut: TextView
    private lateinit var user_name : TextView
    private lateinit var Number : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        user_name = view.findViewById(R.id.name)
        Number = view.findViewById(R.id.phone_num)

        val a = view.context as MainActivity

        a.motion_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        firebaseAuth = FirebaseAuth.getInstance()


        if (a.intent.hasExtra("name")) {
            val name = a.intent.getStringExtra("name")
            Log.d("name",a.intent.getStringExtra("name").toString())
            user_name.text = name
        }
        else{
            user_name.text = "k.v"
        }
        if (a.intent.hasExtra("num")) {
            val number = a.intent.getStringExtra("num")
            Number.text = number
        }
        else{
            Number.text = "+91-"
        }

        LogOut = view.findViewById(R.id.logOut)
        LogOut.setOnClickListener {
            firebaseAuth.signOut()
            val a = (view.context as Activity)
            startActivity(Intent(a, LoginActivity::class.java))
            a.finish()
        }
        return view
    }


}