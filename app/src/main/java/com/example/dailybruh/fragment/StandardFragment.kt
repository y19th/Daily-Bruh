package com.example.dailybruh.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.dailybruh.extension.disableView
import com.example.dailybruh.extension.enableView

open class StandardFragment<T: ViewBinding> : Fragment() {


    var _binding: T? = null
    val binding:T get() = requireNotNull(_binding)


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        this.requireView().enableView()
        super.onViewStateRestored(savedInstanceState)

    }

    override fun onPause() {
        this.requireView().disableView()
        super.onPause()
    }

    override fun onResume() {
        this.requireView().enableView()
        super.onResume()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
        //TODO(make overriding onCreateView to reduce amount of code)
}
