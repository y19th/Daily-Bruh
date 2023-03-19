package com.example.dailybruh.auth

import android.content.Context
import android.widget.Toast
import com.google.firebase.FirebaseException
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
    }

    override fun onVerificationFailed(exc: FirebaseException) {
        when(exc) {
            is FirebaseAuthInvalidCredentialsException -> Toast.makeText(context,"Invalid request",
                Toast.LENGTH_SHORT).show()
            is FirebaseTooManyRequestsException -> Toast.makeText(context,"Too many requests",
                Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(context,"$exc exception", Toast.LENGTH_SHORT).show()
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
}