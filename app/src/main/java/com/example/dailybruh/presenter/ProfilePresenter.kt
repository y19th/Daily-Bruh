package com.example.dailybruh.presenter

import com.example.dailybruh.database.Database
import com.example.dailybruh.interfaces.ProfilePresenterInterface
import com.example.dailybruh.interfaces.ProfileView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfilePresenter(
    private val viewState: ProfileView,
    private val database: Database
) : ProfilePresenterInterface {

    var phoneNumber: String? = null
    var userName: String? = null
    var userNickname: String? = null
    override fun loadData() {

        phoneNumber = Firebase.auth.currentUser?.phoneNumber ?: "no number"
        database.userReference.apply {
            child("name").get().addOnCompleteListener { snapshot ->
                userName = ((snapshot.result.value as String?) ?: "no name").also {
                    viewState.setName(it)
                }
            }
            child("nickname").get().addOnCompleteListener { snapshot ->
                userNickname = ((snapshot.result.value as String?) ?: "no nickname").also {
                    viewState.setNickname(it)
                }
            }
        }
    }

}