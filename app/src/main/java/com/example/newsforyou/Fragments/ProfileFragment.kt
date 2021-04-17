package com.example.newsforyou.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val a = view.context as MainActivity
        a.motion_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        firebaseAuth = FirebaseAuth.getInstance()
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