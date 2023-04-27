package com.example.dailybruh.fragment.dialog.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.dailybruh.R
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.FragmentDialogProfileNicknameBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FragmentDialogProfileNickname(val database: Database) : BottomSheetDialogFragment() {

    private var _binding: FragmentDialogProfileNicknameBinding? = null
    private val binding: FragmentDialogProfileNicknameBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogProfileNicknameBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {

            inputLayout.inputField.hint = getString(R.string.hint_dialog_input_field_nickname)

            doneButton.setOnClickListener {
                inputLayout.apply {
                    when (inputField.text?.length) {
                        null, 0 -> inputLayout.error = "Поле не должно быть пустым"
                        in 1..10 -> {
                            database.nickname(inputField.text.toString())
                            dismiss()
                        }

                        else -> inputLayout.error = "Поле должно содержать не больше 10 символов"
                    }
                    inputField.doOnTextChanged { text, start, before, count ->
                        inputLayout.error = null
                    }
                }
            }
        }
    }
}