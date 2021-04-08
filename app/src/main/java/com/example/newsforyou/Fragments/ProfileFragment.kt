package com.example.newsforyou.Fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.newsforyou.Activities.LoginActivity
import com.example.newsforyou.R
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var LogOut : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
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