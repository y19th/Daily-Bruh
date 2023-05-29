package com.example.dailybruh.presenter

import com.example.dailybruh.database.Database
import com.example.dailybruh.interfaces.AuthPhonePresenterInterface
import com.example.dailybruh.interfaces.AuthPhoneView

class AuthPhonePresenter(
    private val database: Database,
    private val viewState: AuthPhoneView
) : AuthPhonePresenterInterface {

    private var userName : String? = null

    override fun sendData() {
        viewState.navigateNext(userName ?: "null")
    }

    override fun loadData() {
        database.userReference.child("name").get().addOnCompleteListener {
            userName = it.result.value as String?
            sendData()
        }
    }
}