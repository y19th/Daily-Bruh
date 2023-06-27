package com.example.dailybruh.callback

abstract class DataCallback {

    abstract val totalToComplete: Int
    abstract var current: Int

    abstract fun onSuccess(now: Int)

    abstract fun onComplete(now: Int) : Boolean
}