package com.example.dailybruh.fragment.dialog.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.dailybruh.R
import com.example.dailybruh.databinding.FragmentDialogSearchBinding
import com.example.dailybruh.fragment.dialog.StandardDialog


class FragmentDialogSearch(
    private val reset: (String) -> Unit
): StandardDialog<FragmentDialogSearchBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            inputField.doOnTextChanged { _: CharSequence?, _: Int, _: Int, _: Int ->
                inputLayout.error = null
            }
            readyButton.setOnClickListener {
                when (inputField.text!!.length) {
                    in 0..2 -> inputLayout.error = getString(R.string.dialog_search_error_message)
                    else -> reset.invoke(inputField.text.toString())
                }
            }
        }
    }
}