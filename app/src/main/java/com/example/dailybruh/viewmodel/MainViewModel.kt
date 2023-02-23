package com.example.dailybruh.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dailybruh.dataclasses.Message

class MainViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "changed text"
    }
    val text: LiveData<String> = _text
    fun changeText(): LiveData<String> {
        val mes = Message("time is running away",4)
        _text.value = mes.text + " " + mes.count
        return _text
    }
    val funText: LiveData<String> = _text
}