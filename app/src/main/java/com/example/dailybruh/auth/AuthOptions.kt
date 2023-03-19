package com.example.dailybruh.auth

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.example.dailybruh.extension.ToastLong
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class AuthOptions(private val context: Context) {

    lateinit var options: PhoneAuthOptions

    private var _phone: String? = null
    private var _activity: Activity? = null
    private val phone: String get() = _phone!!
    private val activity: Activity get() = _activity!!

    var user : FirebaseUser? = null

    fun createOptions(callbacks: OnVerificationStateChangedCallbacks,
                      phoneNumber: String,
                      activity: Activity,
                      timeout: Long
    ): PhoneAuthOptions {

        _activity = activity
        _phone = phoneNumber

        options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(timeout,TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        return options
    }

    fun signInWithCred(credential: PhoneAuthCredential) {
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if(task.isSuccessful) {
                    user = task.result?.user
                    ToastLong(context,"Authentication success")
                } else {
                    ToastLong(context,"Authentication failed")
                }
            }
    }
}

fun PhoneAuthOptions.verify(){
    PhoneAuthProvider.verifyPhoneNumber(this)
}