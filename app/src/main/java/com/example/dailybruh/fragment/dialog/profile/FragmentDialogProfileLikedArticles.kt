package com.example.dailybruh.fragment.dialog.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailybruh.adapters.NewsPageRecyclerAdapter
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.FragmentDilaogLikedArticlesBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentDialogProfileLikedArticles(private val database: Database) : BottomSheetDialogFragment() {

    private var _binding : FragmentDilaogLikedArticlesBinding? = null
    private val binding : FragmentDilaogLikedArticlesBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDilaogLikedArticlesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerview.apply {
            database.totalLiked().observe(viewLifecycleOwner) {
                adapter = NewsPageRecyclerAdapter(database, viewLifecycleOwner,it)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
}