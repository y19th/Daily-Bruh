package com.example.dailybruh.presenter

import com.example.dailybruh.database.Database
import com.example.dailybruh.interfaces.AuthNamePresenterInterface
import com.example.dailybruh.interfaces.AuthNameView

class AuthNamePresenter(
    private val database: Database,
    private val viewState: AuthNameView
) : AuthNamePresenterInterface {

    private var nickname: String? = null

    override fun sendData() {
        viewState.navigateNext(nickname ?: "null")
    }

    override fun loadData() {
        database.userReference.child("nickname").get().addOnCompleteListener {
            nickname = it.result.value as String?
            sendData()
        }
    }
}