package com.example.newsforyou.Activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.text.bold
import androidx.core.widget.addTextChangedListener
import com.example.newsforyou.Model.UserItem
import com.example.newsforyou.R
import com.example.newsforyou.Utils.UtilMethods.isInternetAvailable
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var fragmentLoginMobile: View
    private lateinit var fragmentLoginOtp: View
    private lateinit var etMobileLogin: EditText
    private lateinit var etCountryCode: EditText
    private lateinit var buttonGetOtp: TextView
    private lateinit var buttonVerify: TextView
    private lateinit var buttonBack: ImageView
    private lateinit var loadingImage: ImageView
    private lateinit var otpScreenText: TextView
    private lateinit var buttonResendOTP: TextView
    private lateinit var otp1: EditText
    private lateinit var otp2: EditText
    private lateinit var otp3: EditText
    private lateinit var otp4: EditText
    private lateinit var otp5: EditText
    private lateinit var otp6: EditText
    private lateinit var inputManager: InputMethodManager
    private lateinit var mobileNumber: String
    private var storedVerificationId: String? = null
    private lateinit var dialog: ProgressDialog
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var userName: EditText
    private var Name: String = ""
    var userItem: UserItem? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        firebaseAuth = FirebaseAuth.getInstance()
        fragmentLoginMobile = findViewById(R.id.mobile_fragment)
        fragmentLoginOtp = findViewById(R.id.otp_fragment)
        etMobileLogin = findViewById(R.id.et_login_mobile)
        etCountryCode = findViewById(R.id.et_country_code)
        buttonGetOtp = findViewById(R.id.button_get_otp)
        buttonVerify = findViewById(R.id.button_otp_verify)
        buttonBack = findViewById(R.id.button_back_otp)
        otpScreenText = findViewById(R.id.tv_otp_screen)
//        loadingImage = findViewById(R.id.loader_button_image)
        buttonResendOTP = findViewById(R.id.button_resend_otp)
        otp1 = findViewById(R.id.otp1)
        otp2 = findViewById(R.id.otp2)
        otp3 = findViewById(R.id.otp3)
        otp4 = findViewById(R.id.otp4)
        otp5 = findViewById(R.id.otp5)
        otp6 = findViewById(R.id.otp6)
        userName = findViewById(R.id.user)


        inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        dialog = ProgressDialog(this)

        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonResendOTP.setOnClickListener {

            if (!isInternetAvailable(this)) {
                Toast.makeText(this, "Internet not connected", Toast.LENGTH_SHORT).show()
            } else if (etMobileLogin.text.toString().length == 10) {
                Toast.makeText(
                    this,
                    "OTP is sent again to entered mobile number. Please wait.",
                    Toast.LENGTH_SHORT
                ).show()

                fragmentLoginMobile.visibility = View.GONE
                fragmentLoginOtp.visibility = View.VISIBLE

                mobileNumber = "${etCountryCode.text}${etMobileLogin.text}"

                val myCustomizedString = SpannableStringBuilder()
                    .append("Enter the OTP sent to ")
                    .bold { append("${etCountryCode.text}-${etMobileLogin.text}") }

                firebaseAuth()
                otpScreenText.text = myCustomizedString
            } else {
                etMobileLogin.requestFocus()
                etMobileLogin.error = "Enter valid mobile number"
            }
        }

        buttonGetOtp.setOnClickListener {
            if (!isInternetAvailable(this)) {
                Toast.makeText(this, "Internet not connected", Toast.LENGTH_SHORT).show()
            } else {
                if (etMobileLogin.text.toString().length == 10) {

                    if (userName.text.toString().isEmpty()) {
                        userName.requestFocus()
                        userName.error = "Enter your name"

                    } else {
                        Name = userName.text.toString()
                        Log.d("user", Name)

                        Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show()

                        fragmentLoginMobile.visibility = View.GONE
                        fragmentLoginOtp.visibility = View.VISIBLE

                        mobileNumber = "${etCountryCode.text}${etMobileLogin.text}"

                        val myCustomizedString = SpannableStringBuilder()
                            .append("Enter the OTP sent to ")
                            .bold { append("${etCountryCode.text}-${etMobileLogin.text}") }

                        firebaseAuth()

                        otpScreenText.text = myCustomizedString

                    }
                } else {
                    etMobileLogin.requestFocus()
                    etMobileLogin.error = "Enter valid mobile number"
                }
            }
        }

        buttonVerify.setOnClickListener {

            if (getOtp().length == 6) {

                dialog.setTitle("Loading")
                dialog.setMessage("Verifying credentials")
                dialog.show()

                buttonVerify.isClickable = false
                verifyOTP(getOtp())

                if (!isInternetAvailable(this)) {
                    Toast.makeText(this, "Internet not connected", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }

            } else {
                Toast.makeText(applicationContext, "Enter Valid OTP", Toast.LENGTH_SHORT).show()
            }
        }

        buttonBack.setOnClickListener {
            fragmentLoginMobile.visibility = View.VISIBLE
            fragmentLoginOtp.visibility = View.GONE
        }

        textChangeListner()
    }


    override fun onBackPressed() {
        if (fragmentLoginOtp.visibility == View.VISIBLE) {
            fragmentLoginOtp.visibility = View.GONE
            fragmentLoginMobile.visibility = View.VISIBLE
        } else {
            finishAffinity()
            super.onBackPressed()
        }
    }


    private fun textChangeListner() {

        etCountryCode.clearFocus()
        etMobileLogin.requestFocus()

        otp1.addTextChangedListener {
            if (otp1.text.toString().length == 1) {
                otp1.clearFocus()
                otp2.requestFocus()
            }
        }
        otp2.addTextChangedListener {
            if (otp2.text.toString().length == 1) {
                otp2.clearFocus()
                otp3.requestFocus()
            }
        }
        otp3.addTextChangedListener {
            if (otp3.text.toString().length == 1) {
                otp3.clearFocus()
                otp4.requestFocus()
            }
        }
        otp4.addTextChangedListener {
            if (otp4.text.toString().length == 1) {
                otp4.clearFocus()
                otp5.requestFocus()
            }
        }
        otp5.addTextChangedListener {
            if (otp5.text.toString().length == 1) {
                otp6.clearFocus()
                otp6.requestFocus()
            }
        }
        otp6.addTextChangedListener {
            if (otp6.text.toString().length + otp5.text.toString().length + otp4.text.toString().length + otp3.text.toString().length
                + otp2.text.toString().length + otp1.text.toString().length == 6
            ) {
                inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
        }
        etMobileLogin.addTextChangedListener {
            if (etMobileLogin.text.toString().length == 10) {
                inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
        }
    }


    private fun getOtp(): String {
        return "${otp1.text}${otp2.text}${otp3.text}${otp4.text}${otp5.text}${otp6.text}"
    }

    private fun firebaseAuth() {

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d("Verification Complete", "onVerificationCompleted:$credential")
                Log.d("Current User: ", firebaseAuth.currentUser?.phoneNumber.toString())
//
//
                signInWithPhoneAuthCredential(credential)
//                verificationComplete()

            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request fo0r verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("Error", "onVerificationFailed ${e.message}")
                dialog.dismiss()
                Toast.makeText(
                    this@LoginActivity,
                    "Verification Failed ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Log.w("invalid", "$e")
                    // ...
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Log.w("smsQuota", "$e")
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("Code Sent", "onCodeSent:$verificationId")
                Toast.makeText(this@LoginActivity, "Verification code sent", Toast.LENGTH_SHORT)
                    .show()

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                // resendToken = token
            }
        }

        Log.d("Mobile: ", mobileNumber)

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mobileNumber, // Phone number to verify
            120, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            this, // Activity (for callback binding)
            callbacks
        ) // OnVerificationStateChangedCallbacks

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        //val credential = PhoneAuthProvider.getCredential(verificationId!!, code)

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Success", "signInWithCredential:success")
                    val hashMap = hashMapOf(
                        "name" to Name,
                        "number" to mobileNumber
                    )
                    FirebaseFirestore.getInstance().collection("Users")
                        .document(FirebaseAuth.getInstance().currentUser!!.uid)
                        .set(hashMap, SetOptions.merge())

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    // Sign in failed, display a message and update the UI
                    dialog.dismiss()
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    Log.w("Error", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(
                            this,
                            "The verification code entered was invalid",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    private fun verifyOTP(otp: String) {

        if (otp != null && storedVerificationId != null) {
            val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, otp)
            signInWithPhoneAuthCredential(credential)
            buttonVerify.isClickable = true
        }
        buttonVerify.isClickable = true

    }


//    fun showLoader(context: Context, title: String, message: String){
//
//        dialog = ProgressDialog(context)
//        dialog.setTitle(title)
//        dialog.setMessage(message)
//        dialog.show()
//
//    }
//
//    private fun verificationComplete(){
//
//        Toast.makeText(this, "User Verified", Toast.LENGTH_SHORT).show()
//        sharedPreferences.edit()
//            .putBoolean("loginStatus", true)
//            .apply()
//
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//        finish()
//
//    }
}