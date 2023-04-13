package com.example.dailybruh.fragment.dialog.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailybruh.R
import com.example.dailybruh.adapters.NewsPageRecyclerAdapter
import com.example.dailybruh.database.constDatabase
import com.example.dailybruh.databinding.FragmentDilaogLikedArticlesBinding
import com.example.dailybruh.extension.navigateTo

class FragmentDialogProfileLikedArticles : Fragment() {

    private var _binding : FragmentDilaogLikedArticlesBinding? = null
    private val binding : FragmentDilaogLikedArticlesBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDilaogLikedArticlesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val database = constDatabase.value!!
        binding.apply {
            recyclerview.apply {
                database.totalLiked().observe(viewLifecycleOwner) {
                    when(it) {
                        0L -> binding.errorLayout.visibility = View.VISIBLE
                        else -> binding.errorLayout.visibility = View.GONE
                    }
                    adapter = NewsPageRecyclerAdapter(database, viewLifecycleOwner,it)
                    layoutManager = LinearLayoutManager(context)
                }
            }
            backButton.backButtonLayout.setOnClickListener {
                view.navigateTo(R.id.liked_articles_to_profile)
            }
        }
    }
}