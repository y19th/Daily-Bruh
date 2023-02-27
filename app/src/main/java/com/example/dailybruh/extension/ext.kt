package com.example.dailybruh.extension

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.iterator
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
fun View.disableView() {
    enableDisableView(this,false)
}
fun View.enableView() {
    enableDisableView(this,true)
}

private fun enableDisableView(view: View,enabled:Boolean) {
    view.isEnabled = enabled
    if(view is ViewGroup) {
        for(child in view) {
            enableDisableView(child,enabled)
        }
    }
}