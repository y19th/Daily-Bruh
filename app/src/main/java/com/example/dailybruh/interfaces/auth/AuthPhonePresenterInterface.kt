package com.example.dailybruh.interfaces.auth

interface AuthPhonePresenterInterface {

    fun sendData()

    fun loadData()

    fun setCallback()

    fun createOptions(phone: String)
}