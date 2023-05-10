package com.example.dailybruh.presenter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.dailybruh.const.BASE_ARTICLE
import com.example.dailybruh.const.BASE_ENDPOINT
import com.example.dailybruh.interfaces.MainPagePresenterInterface
import com.example.dailybruh.interfaces.MainPageView
import com.example.dailybruh.viewmodel.DatabaseViewModel
import com.example.dailybruh.viewmodel.NewsViewModel
import com.example.dailybruh.web.Request
import com.example.dailybruh.web.recentRequest
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainPagePresenter(
    private val viewState: MainPageView,
    private val viewLifecycleOwner: LifecycleOwner,
    private val newsViewModel: NewsViewModel,
    private val databaseViewModel: DatabaseViewModel
): MainPagePresenterInterface {

    val database = databaseViewModel.withLifecycle(lifecycle = viewLifecycleOwner.lifecycle).value
    var userLiked: HashMap<*, *>? = null

    override fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch(CoroutineName("getNewsPresenter")) {
            newsViewModel.also {
                it.getNews(recentRequest?.request ?: Request(BASE_ENDPOINT, BASE_ARTICLE).request)
            }.news.map {
                viewState.setNews(
                    news = it,
                    database = database
                )
            }.stateIn(this)
        }
    }

    private fun getLiked() {
        database.userReference.child("liked").get().addOnCompleteListener {
            userLiked = it.result.value as HashMap<*, *>?
            viewState.setLikes(userLiked)
        }
    }
}