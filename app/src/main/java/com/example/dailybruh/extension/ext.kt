@file:Suppress("Unused")
package com.example.dailybruh.extension

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.iterator
import androidx.navigation.findNavController
import com.example.dailybruh.R
import com.example.dailybruh.activity.MainApp
import com.example.dailybruh.dagger.MainComponent
import com.google.firebase.database.GenericTypeIndicator


val Context.appComponent: MainComponent
    get() = when(this) {
        is MainApp -> appComponent
        else -> this.applicationContext.appComponent
    }

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

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}
fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

private fun enableDisableView(view: View,enabled:Boolean) {
    view.isEnabled = enabled
    if(view is ViewGroup) {
        for(child in view) {
            enableDisableView(child,enabled)
        }
    }
}
fun String?.ifNull(replace: String): String {
    return when(this) {
        null -> {
            replace
        }
        "null" -> {
            replace
        }
        else -> {
            this
        }
    }
}

fun CharSequence.inc(): String {
    return this.toString().toLong().inc().toString()
}

fun CharSequence.dec(): String {
    return this.toString().toLong().dec().toString()
}


fun <T> T.toGenerics(): GenericTypeIndicator<T> {
    return object : GenericTypeIndicator<T>() {}
}

fun toastLong(context: Context, message: String) {
    Toast.makeText(context,message,Toast.LENGTH_LONG).show()
}

fun toastShort(context: Context, message: String) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}

fun TextView.timer(timer: Long,interval: Long) {
    object : CountDownTimer(timer * 1000,interval * 1000) {
        override fun onTick(rem: Long) {
            this@timer.apply {
                isClickable = false
            }.text = "через ${rem.div(1000)} секунд можно будет отправить код снова"
            //TODO(make this shit with placeholders)
        }

        override fun onFinish() {
            this@timer.apply {
                text = resources.getString(R.string.resend_code_text)
            }.isClickable = true
        }

    }.start()
}

fun View.showCustomTab(url: String) {
    val intent = CustomTabsIntent.Builder().build()
    intent.launchUrl(this.context, Uri.parse(url))
}

fun View.showTabOnClick(url: String) {
    this.setOnClickListener {
        this.showCustomTab(url)
    }
}
