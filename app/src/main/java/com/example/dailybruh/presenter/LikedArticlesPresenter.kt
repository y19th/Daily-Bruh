package com.example.dailybruh.presenter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.dailybruh.database.Database
import com.example.dailybruh.dataclasses.PageArticle
import com.example.dailybruh.extension.toGenerics
import com.example.dailybruh.interfaces.LikedArticlesPresenterInterface
import com.example.dailybruh.interfaces.LikedArticlesView
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.locks.ReentrantLock

class LikedArticlesPresenter(
    private val database: Database,
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewState: LikedArticlesView
) : LikedArticlesPresenterInterface {

    var total = 0L
    var userLiked = hashMapOf<String,String>()
    var pageArray = listOf<PageArticle>()

    init {
        database.userReference.child("liked").child("total").get().addOnSuccessListener {
            total = it.value as Long
        }
    }

    override fun loadData() {
        database.userReference.child("liked").get().addOnCompleteListener {
            val map = hashMapOf<String,String>().toGenerics()
            userLiked = it.result.getValue(map) ?: hashMapOf()
            createPage()
        }
    }

    override fun createPage() {

        val lock = ReentrantLock()
        val iterator = userLiked.iterator()

        viewLifecycleOwner.lifecycleScope.launch(CoroutineName("CreatingPageArray")) {
                val defArray : Deferred<List<PageArticle>> = this.async {
                    val tempArray = mutableListOf<PageArticle>()
                    while (iterator.hasNext()) {
                        val item = iterator.next()
                        if (item.key != "total") {
                            database.dataReference.child(item.value).get()
                                .addOnSuccessListener { article ->
                                    tempArray.add(
                                        PageArticle(
                                            id = article.key.toString(),
                                            header = article.child("title").value.toString(),
                                            urlPage = article.child("urlPage").value.toString(),
                                            urlPhoto = article.child("urlPhoto").value.toString(),
                                            author = article.child("author").value.toString()
                                        )
                                    )
                                }
                        }
                    }
                    tempArray
                }
            pageArray = defArray.await()
            viewState.setAdapter(pageArray)
                /*
                lock.withLock {
                    while (iterator.hasNext()) {

                        val item = iterator.next()

                        if (item.key != "total") {
                            database.dataReference.child(item.value).get()
                                .addOnSuccessListener { article ->
                                    pageArray.add(
                                        PageArticle(
                                            id = article.key.toString(),
                                            header = article.child("title").value.toString(),
                                            urlPage = article.child("urlPage").value.toString(),
                                            urlPhoto = article.child("urlPhoto").value.toString(),
                                            author = article.child("author").value.toString()
                                        )
                                    )
                                }
                            this.cancel()
                        }
                    }
                }*/
        }
    }
    suspend fun defToPage(defArray: Deferred<List<PageArticle>>): List<PageArticle> {
        return defArray.await()
    }
}