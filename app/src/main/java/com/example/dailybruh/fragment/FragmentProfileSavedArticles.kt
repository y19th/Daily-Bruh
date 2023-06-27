package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailybruh.R
import com.example.dailybruh.adapters.SavedArticlesRecyclerAdapter
import com.example.dailybruh.databinding.FragmentSavedArticlesBinding
import com.example.dailybruh.dataclasses.PageArticle
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.interfaces.profile.saved.SavedArticlesView
import com.example.dailybruh.presenter.SavedArticlesPresenter

class FragmentProfileSavedArticles : StandardFragment<FragmentSavedArticlesBinding>(), SavedArticlesView {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedArticlesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        SavedArticlesPresenter(
            viewState = this,
            database = database,
            viewLifecycleOwner = viewLifecycleOwner
        ).loadData()

        binding.apply {
            backButton.backButtonLayout.setOnClickListener {
                view.navigateTo(R.id.saved_articles_to_profile)
            }
            recyclerview.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun setAdapter(pageArray: List<PageArticle>) {
        binding.recyclerview.adapter = SavedArticlesRecyclerAdapter(pageArray)
    }
}