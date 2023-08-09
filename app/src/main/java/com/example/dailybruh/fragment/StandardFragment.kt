package com.example.dailybruh.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dailybruh.dagger.MainComponent
import com.example.dailybruh.database.Database
import com.example.dailybruh.extension.appComponent
import com.example.dailybruh.extension.disableView
import com.example.dailybruh.extension.enableView
import com.example.dailybruh.viewmodel.DatabaseViewModel

open class StandardFragment<T> : Fragment(){

    val databaseViewModel: DatabaseViewModel by viewModels()
    var _binding: T? = null
    val binding:T get() = requireNotNull(_binding)
    val database: Database
        get() = databaseViewModel.withLifecycle(lifecycleOwner = viewLifecycleOwner).value

    val mainComponent: MainComponent
        get() = requireContext().appComponent

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
