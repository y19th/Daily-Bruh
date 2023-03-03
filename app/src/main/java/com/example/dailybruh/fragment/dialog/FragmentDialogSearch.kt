package com.example.dailybruh.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import com.example.dailybruh.R
import com.example.dailybruh.databinding.FragmentDialogSearchBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentDialogSearch: BottomSheetDialogFragment() {

    private var _binding: FragmentDialogSearchBinding? = null
    private val binding: FragmentDialogSearchBinding
    get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            textField.doOnTextChanged { text: CharSequence?, start: Int, before: Int, count: Int ->
                    if(text.toString().length < 2)inputLayout.error = getString(R.string.dialog_search_error_message)
                    else inputLayout.error = null
            }
        }
    }

}