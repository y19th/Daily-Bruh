package com.example.dailybruh.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.WindowCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.dailybruh.R
import com.example.dailybruh.const.NEWS_DATA
import com.example.dailybruh.const.constNews
import com.example.dailybruh.databinding.FragmentDialogSearchBinding
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.web.MoshiParse
import com.example.dailybruh.web.Request
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentDialogSearch: BottomSheetDialogFragment() {

    private var _binding: FragmentDialogSearchBinding? = null
    private val binding: FragmentDialogSearchBinding
    get() = _binding!!
    private val model: MoshiParse by viewModels()
    private lateinit var news: News

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setStyle(DialogFragment.STYLE_NORMAL,R.style.DialogStyle)   //TODO(make some animation and round corners to back)
    }

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
            inputField.doOnTextChanged { text: CharSequence?, start: Int, before: Int, count: Int ->
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