package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailybruh.R
import com.example.dailybruh.adapters.NewsPageRecyclerAdapter
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.FragmentDilaogLikedArticlesBinding
import com.example.dailybruh.dataclasses.PageArticle
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.interfaces.LikedArticlesView
import com.example.dailybruh.presenter.LikedArticlesPresenter
import com.example.dailybruh.viewmodel.DatabaseViewModel
import kotlinx.coroutines.Deferred

class FragmentProfileLikedArticles : StandardFragment<FragmentDilaogLikedArticlesBinding>(),LikedArticlesView {

    private val databaseViewModel: DatabaseViewModel by viewModels()
    private lateinit var database : Database
    private lateinit var presenter: LikedArticlesPresenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDilaogLikedArticlesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        database = databaseViewModel.withLifecycle(lifecycleOwner = viewLifecycleOwner).value

        presenter = LikedArticlesPresenter(
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
            adapter = NewsPageRecyclerAdapter(presenter = presenter, pageArray = presenter.pageArray)
            layoutManager = LinearLayoutManager(context)
        }
    }

    suspend fun defToPage(defArray: Deferred<List<PageArticle>>): List<PageArticle> {
        return defArray.await()
    }

}