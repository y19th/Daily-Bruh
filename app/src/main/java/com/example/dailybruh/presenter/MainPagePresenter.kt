package com.example.dailybruh.presenter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.dailybruh.database.Database
import com.example.dailybruh.extension.toGenerics
import com.example.dailybruh.interfaces.mainpage.MainPagePresenterInterface
import com.example.dailybruh.interfaces.mainpage.MainPageView
import com.example.dailybruh.viewmodel.NewsViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch

class MainPagePresenter(
    private val viewState: MainPageView,
    private val viewLifecycleOwner: LifecycleOwner,
    private val newsViewModel: NewsViewModel,
    private val database: Database
): MainPagePresenterInterface {

    override fun sendData (
        likeMap: HashMap<String,String>,
        saveMap: HashMap<String,String>
    ) {
        viewLifecycleOwner.lifecycleScope.launch(CoroutineName("getNewsPresenter")) {
            newsViewModel.news.collect {
                viewState.setNews(
                    news = it,
                    database = database,
                    likesMap = likeMap,
                    savesMap = saveMap
                )
            }
        }
    }

    override fun loadData() {
        var userLiked = hashMapOf<String,String>()
        var userSaved = hashMapOf<String,String>()

        if (Firebase.auth.currentUser != null) {
            database.userReference.get().addOnCompleteListener {
                userLiked = it.result.child("liked").getValue(
                    hashMapOf<String, String>().toGenerics()
                ) ?: hashMapOf()

                userSaved = it.result.child("saved").getValue(
                    hashMapOf<String, String>().toGenerics()
                ) ?: hashMapOf()
            }
        }
        sendData(likeMap = userLiked, saveMap = userSaved)
    }
}