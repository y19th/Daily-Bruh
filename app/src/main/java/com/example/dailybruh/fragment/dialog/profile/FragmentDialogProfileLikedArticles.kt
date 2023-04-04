package com.example.dailybruh.fragment.dialog.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailybruh.adapters.NewsPageRecyclerAdapter
import com.example.dailybruh.const.DATABASE
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.FragmentDilaogLikedArticlesBinding
import com.example.dailybruh.extension.changeHeight
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentDialogProfileLikedArticles(private val database: Database,
                                         private val maxHeight: Int
) : BottomSheetDialogFragment() {

    private var _binding : FragmentDilaogLikedArticlesBinding? = null
    private val binding : FragmentDilaogLikedArticlesBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDilaogLikedArticlesBinding.inflate(inflater,container,false)
        binding.mainLayout.apply {
            maxHeight = this@FragmentDialogProfileLikedArticles.maxHeight
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       // val database = arguments?.getSerializable(DATABASE) as Database         //todo(make this shit undeprecated)

        binding.header.text = binding.mainLayout.maxHeight.toString()


        binding.recyclerview.apply {
            database.totalLiked().observe(viewLifecycleOwner) {
                when(it) {
                    0L -> binding.errorLayout.visibility = View.VISIBLE
                    else -> binding.errorLayout.visibility = View.GONE
                }
                adapter = NewsPageRecyclerAdapter(database, viewLifecycleOwner,it)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
}