package com.example.dailybruh.auth

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dailybruh.R
import com.example.dailybruh.extension.ToastShort
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
            is FirebaseAuthInvalidCredentialsException -> ToastShort(context,"Invalid credential")
            is FirebaseTooManyRequestsException -> ToastShort(context,"Too many requests")
            is FirebaseNetworkException -> ToastShort(context,context.getString(R.string.firebase_error_network))
            else -> ToastShort(context,"$exc exception")
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

    abstract fun onSuccessAuth(view: View, navigationId: Int, arguments: Bundle = Bundle())

    abstract fun onFailedAuth()
}