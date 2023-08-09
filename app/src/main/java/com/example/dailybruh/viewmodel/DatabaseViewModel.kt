package com.example.dailybruh.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailybruh.const.STANDARD_PHONE
import com.example.dailybruh.database.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DatabaseViewModel : ViewModel() {

    private val _database = MutableStateFlow(Database())
    val database: StateFlow<Database> = _database.asStateFlow()

    init {
        updateDatabase()
    }
    private fun updateDatabase() {
        viewModelScope.launch {
            _database.update {
                Database(phone = Firebase.auth.currentUser?.phoneNumber ?: STANDARD_PHONE)
            }
        }
    }
}