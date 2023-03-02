package com.example.dailybruh.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LayoutViewModel : ViewModel() {

    private val _height = MutableLiveData<Int>()
    val height: LiveData<Int> = _height

    fun getHeight(view: View) {
        _height.value = view.height
    }

}