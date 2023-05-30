package com.example.dailybruh.auth

import android.app.Activity
import android.view.View
import com.example.dailybruh.callback.CodeCallback
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class AuthOptions : java.io.Serializable {

    private var _options: PhoneAuthOptions? = null

    private val options: PhoneAuthOptions get() = requireNotNull(_options)

    private var _phone: String? = null
    private var _activity: Activity? = null
    private var _callbacks: CodeCallback? = null

    private val callbacks: CodeCallback get() = requireNotNull(_callbacks)
    private val activity: Activity get() = requireNotNull(_activity)

    private val phone: String get() = requireNotNull(_phone)

    private lateinit var currentView: View

    private var user : FirebaseUser? = null

    fun createOptions(callbacks: CodeCallback,
                      phoneNumber: String,
                      activity: Activity,
                      timeout: Long
    ): PhoneAuthOptions {

        _activity = activity
        _phone = phoneNumber
        _callbacks = callbacks

        _options = PhoneAuthOptions.newBuilder(Firebase.auth)
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
                        view = currentView
                        )
                } else {
                    callbacks.onFailedAuth()
                }
            }
    }

    fun setView(view: View) { currentView = view }
}

fun PhoneAuthOptions.verify(){
    PhoneAuthProvider.verifyPhoneNumber(this)
}