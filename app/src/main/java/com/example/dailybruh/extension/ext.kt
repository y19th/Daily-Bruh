package com.example.dailybruh.extension

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.iterator
import androidx.navigation.findNavController
import com.example.dailybruh.dataclasses.ArticleLikes

fun View.navigateTo(id: Int) {
    this.findNavController().navigate(id)
}
fun View.navigateTo(id: Int,bundle: Bundle) {
    this.findNavController().navigate(id,bundle,null,null)
}
fun View.navigateToWithSerializable(id: Int,item: java.io.Serializable, serId: String) {
    val bundle = Bundle()
    bundle.putSerializable(serId,item)
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

fun ToastLong(context: Context,message: String) {
    Toast.makeText(context,message,Toast.LENGTH_LONG).show()
}

fun ToastShort(context: Context,message: String) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}