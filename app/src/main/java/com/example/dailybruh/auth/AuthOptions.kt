package com.example.dailybruh.auth

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class AuthOptions(private val context: Context) : java.io.Serializable {

    lateinit var options: PhoneAuthOptions

    private var _phone: String? = null
    private var _activity: Activity? = null
    private var _callbacks: CodeCallback? = null

    private val callbacks: CodeCallback get() = _callbacks!!
    private val phone: String get() = _phone!!
    private val activity: Activity get() = _activity!!

    private lateinit var currentView: View
    private lateinit var lifecycleOwner: LifecycleOwner

    var user : FirebaseUser? = null

    fun createOptions(callbacks: CodeCallback,
                      phoneNumber: String,
                      activity: Activity,
                      timeout: Long
    ): PhoneAuthOptions {

        _activity = activity
        _phone = phoneNumber
        _callbacks = callbacks

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
                    callbacks.onSuccessAuth(
                        view = currentView,
                        lifecycleOwner = lifecycleOwner
                        )
                } else {
                    callbacks.onFailedAuth()
                }
            }
    }
    fun setCurrentSetUp(view: View,owner: LifecycleOwner) {
        currentView = view
        lifecycleOwner = owner

    }
}

fun PhoneAuthOptions.verify(){
    PhoneAuthProvider.verifyPhoneNumber(this)
}