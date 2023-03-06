package com.example.dailybruh.fragment.dialog.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dailybruh.databinding.FragmentSettingFromDateBinding

class FragmentSettingsFromDate: Fragment() {

    private var _binding: FragmentSettingFromDateBinding? = null
    private val binding: FragmentSettingFromDateBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingFromDateBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}