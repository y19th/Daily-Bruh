package com.example.dailybruh.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.dailybruh.R
import com.example.dailybruh.databinding.FragmentDialogSearchBinding
import com.example.dailybruh.web.MoshiParse
import com.example.dailybruh.web.Request
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentDialogSearch: BottomSheetDialogFragment() {

    private var _binding: FragmentDialogSearchBinding? = null
    private val binding: FragmentDialogSearchBinding
    get() = _binding!!
    private val model: MoshiParse by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            inputField.doOnTextChanged { _: CharSequence?, _: Int, _: Int, _: Int ->
                    inputLayout.error = null
            }
            readyButton.setOnClickListener {

                when(inputField.text!!.length) {
                    in 0..2 -> inputLayout.error = getString(R.string.dialog_search_error_message)
                    else -> {
                        model.apply {
                            getNews(
                                Request(
                                    "everything",
                                    inputField.text.toString(),
                                    null,
                                    null,
                                    null,
                                    "en"
                                ).request
                            )
                            status.observe(viewLifecycleOwner) {
                                dismiss()
                            }
                        }
                    }
                }
            }
        }
    }

}