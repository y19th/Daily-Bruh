package com.example.dailybruh.fragment

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class StandardFragment< T: ViewBinding> : Fragment() {

    var _binding: T? = null
    val binding:T get() = requireNotNull(_binding)

        //TODO(make overriding onCreateView to reduce amount of code)
}