package com.example.dailybruh.fragment.dialog.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.example.dailybruh.R
import com.example.dailybruh.databinding.FragmentSettingSortingBinding
import com.example.dailybruh.enum.Sort
import com.example.dailybruh.web.sorting

class FragmentSettingsSorting : Fragment() {

    private var _binding: FragmentSettingSortingBinding? = null
    private val binding: FragmentSettingSortingBinding
    get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingSortingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val items = listOf(Sort.POPULARITY.get(), Sort.RELEVANCY.get(), Sort.PUBLISHEDAT.get())
        val adapter = ArrayAdapter(requireContext(), R.layout.menu_list_item, items)
        binding.apply {
            (sortField as? AutoCompleteTextView)?.setAdapter(adapter)
            sortInputLayout.hint = hint()
        }
    }

    private fun hint(): String {
        return when(sorting.value) {
            "popularity" -> Sort.POPULARITY.get()
            "relevancy" -> Sort.RELEVANCY.get()
            "publishedAT" -> Sort.PUBLISHEDAT.get()
            else -> "Без сортировки"
        }
    }
}