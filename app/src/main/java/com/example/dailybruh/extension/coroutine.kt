@file:Suppress("Unused")
package com.example.dailybruh.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



suspend fun withMain(block: suspend CoroutineScope.() -> Unit) {
    withContext(Dispatchers.Main,block)
}



