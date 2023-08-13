package com.example.dailybruh.interfaces.auth

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import com.example.dailybruh.fragment.auth.FragmentAuthPhone

interface AuthPhoneView {

    val viewContext: Context
    val viewFragment: FragmentAuthPhone
    fun navigateNext(name : String,codeView: View)

    fun navigateToVerCode(@IdRes id: Int, bundle: Bundle = Bundle() )
}