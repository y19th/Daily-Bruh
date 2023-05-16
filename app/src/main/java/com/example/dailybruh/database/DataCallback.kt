package com.example.dailybruh.database

abstract class DataCallback {

    abstract val totalToComplete: Int


    abstract fun onSuccess(now: Int)

    abstract fun onComplete(now: Int) : Boolean
}