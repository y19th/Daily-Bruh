package com.example.dailybruh.presenter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.dailybruh.const.BASE_ARTICLE
import com.example.dailybruh.const.BASE_ENDPOINT
import com.example.dailybruh.extension.toGenerics
import com.example.dailybruh.interfaces.MainPagePresenterInterface
import com.example.dailybruh.interfaces.MainPageView
import com.example.dailybruh.viewmodel.DatabaseViewModel
import com.example.dailybruh.viewmodel.NewsViewModel
import com.example.dailybruh.web.Request
import com.example.dailybruh.web.recentRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainPagePresenter(
    private val viewState: MainPageView,
    private val viewLifecycleOwner: LifecycleOwner,
    private val newsViewModel: NewsViewModel,
    databaseViewModel: DatabaseViewModel
): MainPagePresenterInterface {

    val database = databaseViewModel.withLifecycle(lifecycle = viewLifecycleOwner.lifecycle).value
    var userLiked: HashMap<String,String> = hashMapOf()

    override fun sendData() {
        viewLifecycleOwner.lifecycleScope.launch(CoroutineName("getNewsPresenter")) {
            newsViewModel.also {
                it.getNews(recentRequest?.request ?: Request(BASE_ENDPOINT, BASE_ARTICLE).request)
            }.news.map {
                viewState.setNews(
                    news = it,
                    database = database,
                    likesMap = userLiked
                )
            }.stateIn(this)
        }
    }

    override fun loadData() {
        if (Firebase.auth.currentUser != null) {
            database.userReference.child("liked").get().addOnCompleteListener {
                val map = hashMapOf<String, String>().toGenerics()
                userLiked = it.result.getValue(map) ?: hashMapOf()
                sendData()
            }
        } else {
            sendData()
        }
    }
}