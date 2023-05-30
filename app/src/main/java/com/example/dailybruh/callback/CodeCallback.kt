package com.example.dailybruh.callback

import android.content.Context
import android.view.View
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

    override fun onVerificationCompleted(cred: PhoneAuthCredential) {
        onInstantAuth(cred)
        //TODO(make resending fun somehow)
    }

    override fun onVerificationFailed(exc: FirebaseException) {
        when(exc) {
            is FirebaseAuthInvalidCredentialsException -> toastShort(context,context.getString(R.string.firebase_error_invalid_credential))
            is FirebaseTooManyRequestsException -> toastShort(context,context.getString(R.string.firebase_error_too_many_requests))
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

    abstract fun onSuccessAuth(view: View)

    abstract fun onInstantAuth(cred: PhoneAuthCredential)

    abstract fun onFailedAuth()
}