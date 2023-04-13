package com.example.dailybruh.auth

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.dailybruh.R
import com.example.dailybruh.extension.toastShort
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

abstract class CodeCallback(private val context: Context) : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
    private var _token: PhoneAuthProvider.ForceResendingToken? = null
    private val token: PhoneAuthProvider.ForceResendingToken get() = _token!!

    private var _verId: String? = null
    private val verId: String get() = _verId!!

    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
        //TODO(make transition between fragments if instant auth)
        //TODO(make resending fun)
    }

    override fun onVerificationFailed(exc: FirebaseException) {
        when(exc) {
            is FirebaseAuthInvalidCredentialsException -> toastShort(context,"Invalid credential")
            is FirebaseTooManyRequestsException -> toastShort(context,"Too many requests")
            is FirebaseNetworkException -> toastShort(context,context.getString(R.string.firebase_error_network))
            else -> toastShort(context,"$exc exception")
        }
    }

    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
        _token = token
        _verId = verificationId
        stepOnCodeSent()
    }

    fun token() = token

    fun verificationId() = verId

    abstract fun stepOnCodeSent()

    abstract fun onSuccessAuth(view: View, lifecycleOwner: LifecycleOwner, arguments: Bundle = Bundle())

    abstract fun onFailedAuth()
}