package com.example.dailybruh.presenter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.dailybruh.callback.DataCallback
import com.example.dailybruh.database.Database
import com.example.dailybruh.dataclasses.PageArticle
import com.example.dailybruh.extension.toGenerics
import com.example.dailybruh.interfaces.profile.saved.SavedArticlesPresenterInterface
import com.example.dailybruh.interfaces.profile.saved.SavedArticlesView
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class SavedArticlesPresenter(
    private val viewState: SavedArticlesView,
    private val database: Database,
    private val viewLifecycleOwner: LifecycleOwner
) : SavedArticlesPresenterInterface {

    private var userSaved: HashMap<String,String> = hashMapOf()
    private var _pageArray = mutableListOf<PageArticle>()
    private val pageArray: List<PageArticle> get() = _pageArray

    override fun loadData() {
        database.userReference.child("saved").get().addOnCompleteListener {
            val map = hashMapOf<String,String>().toGenerics()
            userSaved = it.result.getValue(map) ?: hashMapOf()
            createPage()
        }
    }

    private fun createPage() {
        val iterator = userSaved.iterator()

        viewLifecycleOwner.lifecycleScope.launch(CoroutineName("CreatingPageArray")) {
            ReentrantLock().withLock {
                while (iterator.hasNext()) {
                    val item = iterator.next()
                    if (item.key != "total") {
                        getItemFromDatabase(itemId = item.value, callback = createCallback())
                    }
                }
            }
        }
    }

    private fun getItemFromDatabase(itemId: String,callback: DataCallback) {
        database.dataReference.child(itemId).get()
            .addOnCompleteListener { article ->
                _pageArray.add(
                    PageArticle(
                        id = article.result.key.toString(),
                        header = article.result.child("title").value.toString(),
                        urlPage = article.result.child("urlPage").value.toString(),
                        urlPhoto = article.result.child("urlPhoto").value.toString(),
                        author = article.result.child("author").value.toString(),
                        description = article.result.child("description").value.toString()
                    )
                )
                callback.onSuccess(_pageArray.size)
            }
    }

    private fun createCallback() = object : DataCallback() {
        override val totalToComplete: Int
            get() = userSaved.size - 1
        override var current: Int = 0

        override fun onSuccess(now: Int) {
            if(!(onComplete(now)))current++
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