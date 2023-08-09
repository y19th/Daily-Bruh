@file:Suppress("Unused")
package com.example.dailybruh.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


suspend fun withMain(lambda: suspend CoroutineScope.() -> Unit) {
    lambda.invoke(CoroutineScope(Dispatchers.Main))
}


