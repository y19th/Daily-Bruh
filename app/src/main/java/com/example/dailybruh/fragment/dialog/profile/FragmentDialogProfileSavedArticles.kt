package com.example.dailybruh.fragment.dialog.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.FragmentDialogSavedArticlesBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentDialogProfileSavedArticles(database: Database): BottomSheetDialogFragment() {

    private var _binding: FragmentDialogSavedArticlesBinding? = null
    private val binding: FragmentDialogSavedArticlesBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogSavedArticlesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}