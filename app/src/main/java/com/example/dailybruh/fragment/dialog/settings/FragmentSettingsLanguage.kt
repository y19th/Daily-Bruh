package com.example.dailybruh.fragment.dialog.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.dailybruh.R
import com.example.dailybruh.databinding.FragmentSettingLanguageBinding
import com.example.dailybruh.enum.Language
import com.example.dailybruh.web.language

class FragmentSettingsLanguage: Fragment() {

    private var _binding: FragmentSettingLanguageBinding? = null
    private val binding: FragmentSettingLanguageBinding
     get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingLanguageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val items = listOf(Language.RUSSIAN.get(),Language.ENGLISH.get())
        val adapter = ArrayAdapter(requireContext(),R.layout.menu_list_item,items)
        binding.apply {
            (langField as? AutoCompleteTextView)?.setAdapter(adapter)
            langInputLayout.hint  = hint()
            langField.doOnTextChanged { _, _, _, _ ->
                when(langField.text.toString()) {
                    Language.RUSSIAN.get() -> {
                        language("ru")
                        langInputLayout.hint = hint()

                    }
                     Language.ENGLISH.get() -> {
                         language("en")
                         langInputLayout.hint = hint()
                     }
                }
            }
        }
    }

    private fun hint(): String {
        return if(language.value == "ru")Language.RUSSIAN.get() else Language.ENGLISH.get()
    }

}