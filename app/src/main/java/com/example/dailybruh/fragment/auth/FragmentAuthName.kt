package com.example.dailybruh.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.dailybruh.R
import com.example.dailybruh.databinding.FragmentAuthNameBinding
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.fragment.StandardFragment
import com.example.dailybruh.viewmodel.DatabaseViewModel

class FragmentAuthName: StandardFragment<FragmentAuthNameBinding>() {

    private val databaseViewModel: DatabaseViewModel by viewModels()
    private val database = databaseViewModel.withLifecycle(lifecycleOwner = viewLifecycleOwner).value

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthNameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            backButton.backButtonLayout.setOnClickListener {
                view.navigateTo(R.id.auth_name_to_profile)
            }
            inputField.doOnTextChanged { _, _, _, _ ->
                inputLayout.error = null
                inputLayout.helperText = null

            }
            continueButton.setOnClickListener {
                if(inputField.editableText.isEmpty())inputLayout.error = "unable to have zero-chars name"
                else database.changeName(inputField.text.toString())
            }
        }
    }
}