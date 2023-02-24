package com.example.dailybruh.extension

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController

fun View.navigateTo(id: Int) {
    this.findNavController().navigate(id)
}
fun View.navigateTo(id: Int,bundle: Bundle) {
    this.findNavController().navigate(id,bundle,null,null)
}

fun View.changeParams(width: Int,height: Int) {
    val params = this.layoutParams
    params.height = height
    params.width = width
    this.layoutParams = params
}
fun View.changeHeight(height: Int) {
    val params = this.layoutParams
    params.height = height
    this.layoutParams = params
}

fun View.changeWidth(width: Int) {
    val params = this.layoutParams
    params.width = width
    this.layoutParams = params
}