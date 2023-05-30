package com.example.dailybruh.interfaces.auth

import android.content.Context
import android.view.View
import com.example.dailybruh.fragment.auth.FragmentAuthPhone

interface AuthPhoneView {

    val viewContext: Context
    val viewFragment: FragmentAuthPhone
    fun navigateNext(name : String,codeView: View)
}