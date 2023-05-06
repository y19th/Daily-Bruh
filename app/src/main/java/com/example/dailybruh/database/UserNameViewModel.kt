package com.example.dailybruh.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserNameViewModel : ViewModel() {

    private var _nameFlow = MutableStateFlow(String())
    val nameFlow: StateFlow<String> = _nameFlow.asStateFlow()

    fun getName() {
        viewModelScope.launch(Dispatchers.IO + CoroutineName("ExtractName")) {
            _nameFlow.collect()
        }
    }
    fun setName(newName: String) {
        viewModelScope.launch(Dispatchers.IO + CoroutineName("SetName")) {
            _nameFlow.update { newName }
        }
    }
}