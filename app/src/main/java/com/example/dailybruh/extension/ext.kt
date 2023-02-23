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