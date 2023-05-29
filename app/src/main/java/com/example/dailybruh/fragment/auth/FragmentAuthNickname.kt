package com.example.dailybruh.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.dailybruh.R
import com.example.dailybruh.databinding.FragmentAuthNicknameBinding
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.fragment.StandardFragment
import com.example.dailybruh.viewmodel.DatabaseViewModel

class FragmentAuthNickname : StandardFragment<FragmentAuthNicknameBinding>() {

    private val databaseViewModel: DatabaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthNicknameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val database = databaseViewModel.withLifecycle(lifecycleOwner = viewLifecycleOwner).value

        binding.apply {
            backButton.backButtonLayout.setOnClickListener {
                view.navigateTo(R.id.auth_nickname_to_profile)
            }
            inputField.doOnTextChanged { _, _, _, _ ->
                inputLayout.error = null
                inputLayout.helperText = null

            }
            continueButton.setOnClickListener {
                if(inputField.editableText.isEmpty())inputLayout.error = "unable to have zero-chars name"
                else {
                    database.changeNickname(inputField.text.toString())
                    view.navigateTo(R.id.auth_nickname_to_profile)
                }
            }
        }
    }

}