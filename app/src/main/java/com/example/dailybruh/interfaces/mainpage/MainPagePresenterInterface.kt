package com.example.dailybruh.interfaces.mainpage

interface MainPagePresenterInterface {

    fun loadData()
    fun sendData(likeMap:HashMap<String,String>, saveMap:HashMap<String,String>)
}