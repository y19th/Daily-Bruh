package com.example.dailybruh.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.dailybruh.R
import com.example.dailybruh.databinding.FragmentDialogSearchBinding
import com.example.dailybruh.viewmodel.NewsViewModel
import com.example.dailybruh.web.Request
import com.example.dailybruh.web.from
import com.example.dailybruh.web.sorting
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentDialogSearch(
    private val viewModel: NewsViewModel
): BottomSheetDialogFragment() {

    private var _binding: FragmentDialogSearchBinding? = null
    private val binding: FragmentDialogSearchBinding
    get() = _binding!!
//    private val model: NewsViewModel by viewModels()

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
                        viewModel.apply {
                            getNews(
                                Request(
                                    "everything",
                                    inputField.text.toString(),
                                    from.value,
                                    null,
                                    sorting.value,
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