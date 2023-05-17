package com.example.dailybruh.fragment.dialog.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.dailybruh.R
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.FragmentDialogProfileNameBinding
import com.example.dailybruh.fragment.dialog.StandardDialog

class FragmentDialogProfileName(
    private val database: Database,
    private val update: (String) -> Unit
):  StandardDialog<FragmentDialogProfileNameBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogProfileNameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {

            inputLayout.inputField.hint = getString(R.string.hint_dialog_input_field_name)

            doneButton.setOnClickListener {
                inputLayout.apply {
                    when (inputField.text?.length) {
                        null, 0 -> inputLayout.error = "Поле не должно быть пустым"
                        in 1..10 -> {
                            database.changeName(inputField.text.toString())
                            update.invoke(inputField.text.toString())
                            dismiss()
                        }

                        else -> inputLayout.error = "Поле должно содержать не больше 10 символов"
                    }
                    inputField.doOnTextChanged { _, _, _, _ ->
                        inputLayout.error = null
                    }
                }
            }
        }
    }
}