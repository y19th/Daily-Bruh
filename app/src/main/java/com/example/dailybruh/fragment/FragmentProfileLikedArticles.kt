package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailybruh.R
import com.example.dailybruh.adapters.LikedArticlesRecyclerAdapter
import com.example.dailybruh.databinding.FragmentDilaogLikedArticlesBinding
import com.example.dailybruh.dataclasses.PageArticle
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.interfaces.profile.liked.LikedArticlesView
import com.example.dailybruh.presenter.LikedArticlesPresenter

class FragmentProfileLikedArticles : StandardFragment<FragmentDilaogLikedArticlesBinding>(),
    LikedArticlesView {

    private var _presenter: LikedArticlesPresenter? = null
    private val presenter: LikedArticlesPresenter get() = requireNotNull(_presenter)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDilaogLikedArticlesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        _presenter = LikedArticlesPresenter(
            database = database,
            viewLifecycleOwner = viewLifecycleOwner,
            viewState = this
        ).also { it.loadData() }
        binding.apply {
            backButton.backButtonLayout.setOnClickListener {
                view.navigateTo(R.id.liked_articles_to_profile)
            }
        }
    }

    override fun setAdapter(pageArray: List<PageArticle>) {
        binding.recyclerview.apply {
            when (presenter.total) {
                0L -> binding.errorLayout.visibility = View.VISIBLE
                else -> binding.errorLayout.visibility = View.GONE
            }
            adapter = LikedArticlesRecyclerAdapter(pageArray = pageArray)
            layoutManager = LinearLayoutManager(context)
        }
    }
}