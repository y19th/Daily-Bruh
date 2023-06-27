package com.example.dailybruh.presenter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.dailybruh.callback.DataCallback
import com.example.dailybruh.database.Database
import com.example.dailybruh.dataclasses.PageArticle
import com.example.dailybruh.extension.toGenerics
import com.example.dailybruh.interfaces.profile.liked.LikedArticlesPresenterInterface
import com.example.dailybruh.interfaces.profile.liked.LikedArticlesView
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class LikedArticlesPresenter(
    private val database: Database,
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewState: LikedArticlesView
) : LikedArticlesPresenterInterface {

    var total = 0L
    var userLiked = hashMapOf<String,String>()
    private var tempArray = mutableListOf<PageArticle>()
    private val pageArray get() = tempArray

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

        val iterator = userLiked.iterator()

        viewLifecycleOwner.lifecycleScope.launch(CoroutineName("CreatingPageArray")) {
            ReentrantLock().withLock {
                    while (iterator.hasNext()) {
                        val item = iterator.next()
                        if (item.key != "total") {
                            itemFromDatabase(item.value, callback = createCallback())
                        }
                    }
                }
        }

    }
    private fun itemFromDatabase(itemId: String,callback: DataCallback) {

        var tempPage: PageArticle
        database.dataReference.child(itemId).get()
            .addOnCompleteListener {article ->
                tempPage = PageArticle(
                    id = article.result.key.toString(),
                    header = article.result.child("title").value.toString(),
                    urlPage = article.result.child("urlPage").value.toString(),
                    urlPhoto = article.result.child("urlPhoto").value.toString(),
                    author = article.result.child("author").value.toString()
                )
                addPageToList(tempPage)
                callback.onSuccess(tempArray.size)
        }
    }
    private fun addPageToList(pageArticle: PageArticle) {
        tempArray.add(pageArticle)
    }

    private fun createCallback() = object : DataCallback() {
        override val totalToComplete: Int
            get() = userLiked.size - 1

        override var current = 0

        override fun onSuccess(now: Int) {
            if(!onComplete(now))current++
        }

        override fun onComplete(now: Int): Boolean {
            if(totalToComplete <= now) {
                viewState.setAdapter(pageArray)
                return true
            }
            return false
        }

    }
}