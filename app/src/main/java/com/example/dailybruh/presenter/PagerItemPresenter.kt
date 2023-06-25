package com.example.dailybruh.presenter

import com.example.dailybruh.database.Database
import com.example.dailybruh.interfaces.mainpage.pager.PagerItemPresenterInterface
import com.example.dailybruh.interfaces.mainpage.pager.PagerItemView

class PagerItemPresenter(
    private val viewState: PagerItemView,
    private val database: Database,
    private val itemId: String,
    private val mapLikes: HashMap<String,String>,
    private val mapSaves: HashMap<String,String>
) : PagerItemPresenterInterface {

    var isLiked: Boolean = false
    var isSaved: Boolean = false
    private var likeTotal = 0L
    private var saveTotal = 0L

    init {
        database.userReference.child("liked").child("total").get().addOnSuccessListener {
            likeTotal = it.value as Long
        }
        database.userReference.child("saved").child("total").get().addOnSuccessListener {
            saveTotal = it.value as Long
        }
    }

    override fun getLikes() {
        database.dataReference.child(itemId).child("likes").get().addOnCompleteListener {
            viewState.setLikes(it.result.value as Long? ?: 0L)
            isLiked()
        }
    }

    override fun getSaves() {
        viewState.setSaves()
        isSaved()
    }

    override fun isLiked() {
        viewState.checkIsLiked(
            when(mapLikes.size) {
            0 -> {
                isLiked = false
                false
            }
            else -> {
                isLiked = mapLikes.containsValue(itemId)
                isLiked
            }
        })
    }

    override fun isSaved() {
        viewState.checkIsSaved(
            when(mapSaves.size) {
                0 -> {
                    isSaved = false
                    false
                }
                else -> {
                    isSaved = mapSaves.containsValue(itemId)
                    isSaved
                }
            }
        )
    }

    override fun changeLikes(long: Long) {
        when(isLiked) {
            true -> {
                database.dataReference.child(itemId).child("likes").setValue(long)
                removeLike()
            }
            false -> {
                database.dataReference.child(itemId).child("likes").setValue(long)
                addLike()
            }
        }
    }


    override fun changeSaveState() {
        when(isSaved) {
            true -> removeSave()
            false -> addSave()
        }
    }

    private fun addSave() {
        when(val itemPull = mapSaves.checkPull()) {
            null -> {
                mapSaves["id$saveTotal"] = itemId
                saveTotal++
                database.addSave(itemId = "id${saveTotal - 1}", value = itemId,total = saveTotal)
                isSaved()
            }
            else -> {
                mapSaves[itemPull] = itemId
                saveTotal++
                database.addSave(itemId = itemPull, value = itemId, total = saveTotal)
                isSaved()
            }
        }
    }

    private fun removeSave() {
        val iterator = mapSaves.iterator()
        while (iterator.hasNext()) {

            val item = iterator.next()
            val itemKey = item.key

            if(item.value == itemId) {
                saveTotal--
                database.removeSave(itemId = itemKey, total = saveTotal)
                iterator.remove()
            }
        }
        isSaved()
    }

    private fun removeLike() {
        val iterator = mapLikes.iterator()
        while(iterator.hasNext()) {

            val item = iterator.next()
            val itemKey = item.key

            if(item.value == itemId) {
                likeTotal--
                database.removeLike(itemId = itemKey, total = likeTotal)
                iterator.remove()
            }
        }
        isLiked()
    }

    private fun addLike() {
        when(val itemPull = mapLikes.checkPull()) {
            null -> {
                mapLikes["id$likeTotal"] = itemId
                likeTotal++
                database.addLike(itemId = "id${likeTotal - 1}", value = itemId,total = likeTotal)
                isLiked()
            }
            else -> {
                mapLikes[itemPull] = itemId
                likeTotal++
                database.addLike(itemId = itemPull, value = itemId, total = likeTotal)
                isLiked()
            }
        }
    }

    private fun HashMap<String,String>.checkPull(): String? {   //checks if map has spaces between items( item0, item1 , item3)
        for(iterCount in 0..this.size) {                  //and returns id of searched element
            if(this["id$iterCount"].isNullOrEmpty()) {          //or null if pull correct
                return "id$iterCount"
            }
        }
        return null
    }

}