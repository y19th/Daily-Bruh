package com.example.dailybruh.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.WindowCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.dailybruh.R
import com.example.dailybruh.const.NEWS_DATA
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


//        WindowCompat.setDecorFitsSystemWindows(requireActivity().window,false)

        binding.apply {
            /*textField.doOnTextChanged { text: CharSequence?, start: Int, before: Int, count: Int ->
                    if(text.toString().length < 2) {
                        inputLayout.error = getString(R.string.dialog_search_error_message)
                    }
                    else  {
                        inputLayout.error = null
                    }
            }
            doneButton.setOnClickListener {
                if (textField.text!!.length >= 2) {
                    val observer = Observer<String> {
                        news = model.news.value!!
                        navigateUp()
                    }
                    model.apply {
                        getNews(
                            Request(
                                "everything",
                                textField.text.toString(),
                                null,
                                null,
                                null,
                                "en"
                            ).request
                        )
                        status.observe(viewLifecycleOwner, observer)
                    }
                }
            }*/
        }
    }
    private fun navigateUp() {
        val bundle = Bundle()
        bundle.putSerializable(NEWS_DATA,news)
        parentFragment?.view?.navigateTo(R.id.newspage_to_searched,bundle)
    }

}