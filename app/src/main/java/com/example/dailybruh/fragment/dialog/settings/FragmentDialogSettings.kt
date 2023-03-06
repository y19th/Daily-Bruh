package com.example.dailybruh.fragment.dialog.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.dailybruh.R
import com.example.dailybruh.databinding.FragmentDialogSettingsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentDialogSettings : BottomSheetDialogFragment() {

    private var _binding : FragmentDialogSettingsBinding? = null
    private val binding: FragmentDialogSettingsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.commit {
            replace<FragmentSettingsLanguage>(R.id.lang_container,null,null)
            replace<FragmentSettingsSorting>(R.id.sort_container,null,null)
            replace<FragmentSettingsFromDate>(R.id.date_container,null,null)
            setReorderingAllowed(true)
        }
    }
}