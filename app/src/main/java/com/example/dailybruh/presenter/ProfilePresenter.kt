package com.example.dailybruh.presenter

import com.example.dailybruh.database.Database
import com.example.dailybruh.interfaces.ProfilePresenterInterface
import com.example.dailybruh.interfaces.ProfileViewInterface

class ProfilePresenter(
    private val profileInterface: ProfileViewInterface,
    private val database: Database
) : ProfilePresenterInterface {
    override fun loadData() {
        database.userReference.apply {
            child("name").get().addOnCompleteListener {
                profileInterface.setName( (it.result.value as String?) ?: "no name")
            }
            child("nickname").get().addOnCompleteListener {
                profileInterface.setNickname( (it.result.value as String?) ?: "no nickname")
            }
        }
    }

}