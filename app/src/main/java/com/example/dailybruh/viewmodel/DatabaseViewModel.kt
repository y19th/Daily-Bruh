package com.example.dailybruh.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.dailybruh.const.STANDARD_PHONE
import com.example.dailybruh.database.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DatabaseViewModel : ViewModel() {

    private val _database = MutableStateFlow(Database())
    val database: StateFlow<Database> = _database.asStateFlow()

    init {
        val phone = Firebase.auth.currentUser?.phoneNumber ?: STANDARD_PHONE
        _database.update { Database(phone) }
    }

    fun withLifecycle(lifecycle: Lifecycle? = null,lifecycleOwner: LifecycleOwner? = null): StateFlow<Database> {
        _database.value.setLifecycle(
            lOwner = lifecycleOwner?.let { lifecycleOwner },
            lcycle = lifecycle?.let { lifecycle }
        )
        return database
    }

}