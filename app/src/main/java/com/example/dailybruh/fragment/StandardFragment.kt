package com.example.dailybruh.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dailybruh.database.Database
import com.example.dailybruh.extension.disableView
import com.example.dailybruh.extension.enableView
import com.example.dailybruh.viewmodel.DatabaseViewModel

open class StandardFragment<T> : Fragment() {

    val databaseViewModel: DatabaseViewModel by viewModels()
    var _binding: T? = null

    //val twr: T by viewBinding(CreateMethod.INFLATE)
    val binding:T get() = requireNotNull(_binding)
    val database: Database
        get() = databaseViewModel.withLifecycle(lifecycleOwner = viewLifecycleOwner).value

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
