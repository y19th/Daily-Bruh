package com.example.dailybruh.presenter

import android.os.Bundle
import android.view.View
import com.example.dailybruh.R
import com.example.dailybruh.auth.AuthOptions
import com.example.dailybruh.callback.CodeCallback
import com.example.dailybruh.auth.verify
import com.example.dailybruh.const.AUTH_OPTIONS
import com.example.dailybruh.const.STANDARD_PHONE
import com.example.dailybruh.const.VERIFICATION_ID
import com.example.dailybruh.database.Database
import com.example.dailybruh.extension.enableView
import com.example.dailybruh.extension.ifNull
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.extension.toastLong
import com.example.dailybruh.interfaces.auth.AuthPhonePresenterInterface
import com.example.dailybruh.interfaces.auth.AuthPhoneView
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthPhonePresenter(
    private val viewState: AuthPhoneView
) : AuthPhonePresenterInterface {

    private var userName : String? = null
    private var _codeView: View? = null
    private var _callback: CodeCallback? = null

    private val codeView: View get() = requireNotNull(_codeView)
    private val callback: CodeCallback get() = requireNotNull(_callback)
    val authOptions = AuthOptions()

    override fun sendData() {
        viewState.navigateNext(userName ?: "null",codeView)
    }

    override fun loadData() {
        Database(Firebase.auth.currentUser!!.phoneNumber.ifNull(STANDARD_PHONE))
            .userReference.child("name").get().addOnCompleteListener {
            userName = it.result.value as String?
            sendData()
        }
    }

    override fun setCallback() {
        _callback = object : CodeCallback(context = viewState.viewContext) {

            override fun stepOnCodeSent() {
                viewState.viewFragment.requireView().enableView()
                toastLong(viewState.viewContext,"code sent")
                val bundle = Bundle()
                bundle.apply {
                    putSerializable(AUTH_OPTIONS,authOptions)
                }.putString(VERIFICATION_ID,this.verificationId())
                viewState.viewFragment.requireView().navigateTo(
                    id =  R.id.auth_phone_to_auth_vercode,
                    bundle = bundle
                )

            }
            override fun onSuccessAuth(
                view: View
            ) {
                _codeView = view
                loadData()
            }

            override fun onInstantAuth(credential: PhoneAuthCredential) {
                authOptions.signInWithCred(credential)
            }

            override fun onFailedAuth() {
                toastLong(viewState.viewContext,"Ошибка авторизации")
            }
        }
    }

    override fun createOptions(phone: String) {
        authOptions.createOptions(
            callbacks = callback,
            phoneNumber = phone,
            activity = viewState.viewFragment.requireActivity(),
            timeout = 60L
        ).verify()

    }
}