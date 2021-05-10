package com.example.newsforyou.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import com.example.newsforyou.Activities.LoginActivity
import com.example.newsforyou.Activities.MainActivity
import com.example.newsforyou.Model.UserItem
import com.example.newsforyou.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ProfileFragment : Fragment() {

    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var LogOut: TextView
    private lateinit var name: TextView
    private lateinit var Number: TextView
    lateinit var camBTn: ImageView
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    val userItem: UserItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        name = view.findViewById(R.id.name)
        Number = view.findViewById(R.id.phone_num)
        camBTn = view.findViewById(R.id.cam)

        camBTn.setOnClickListener {
            launchGallery()
        }

        val a = view.context as MainActivity

        a.motion_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        firebaseAuth = FirebaseAuth.getInstance()


        getDetails()


        LogOut = view.findViewById(R.id.logOut)
        LogOut.setOnClickListener {
            firebaseAuth.signOut()
            val a = (view.context as Activity)
            startActivity(Intent(a, LoginActivity::class.java))
            a.finish()
        }
        return view
    }

    private fun getDetails() {
        FirebaseFirestore.getInstance().collection("Users").document(firebaseAuth.currentUser!!.uid)
            .addSnapshotListener { v, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (v != null) {
                    name.text = v.get("name").toString()
                    Number.text = v.get("number").toString()
                    Log.d("details", v.get("name").toString())
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            uploadImage(view, data!!.data!!)
        }
    }

    private fun uploadImage(view: View?, filePath: Uri?) {
        if (filePath != null) {
//            val hashMap = hashMapOf(
//                "image" to filePath.toString()
//            )
//            FirebaseFirestore.getInstance().collection("Users")
//                .document(FirebaseAuth.getInstance().currentUser!!.uid)
//                .set(hashMap, SetOptions.merge())
//                .addOnCompleteListener {
//                    if (it.isSuccessful){
//                        Toast.makeText(view!!.context, "Image Uploaded", Toast.LENGTH_SHORT).show()
//                    }
//                    else{
//                        Toast.makeText(view!!.context, it.toString(), Toast.LENGTH_SHORT).show()
//                        Log.d("image", it.toString())
//                    }
        }
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }


}