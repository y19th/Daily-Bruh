package com.example.dailybruh.presenter

import com.example.dailybruh.database.Database
import com.example.dailybruh.interfaces.PagerItemPresenterInterface
import com.example.dailybruh.interfaces.PagerItemView

class PagerItemPresenter(
    private val viewState: PagerItemView,
    private val database: Database,
    private val itemId: String,
    private val mapLikes: HashMap<String,String>
) : PagerItemPresenterInterface {

    var isLiked: Boolean = false
    var total = 0L

    init {
        database.userReference.child("liked").child("total").get().addOnSuccessListener {
            total = it.value as Long
        }
    }

    override fun getLikes() {
        database.dataReference.child(itemId).child("likes").get().addOnCompleteListener {
            viewState.setLikes(it.result.value as Long? ?: 0L)
            isLiked()
        }
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

    private fun removeLike() {
        val iterator = mapLikes.iterator()
        while(iterator.hasNext()) {

            val item = iterator.next()
            val itemKey = item.key

            if(item.value == itemId) {
                total--
                database.removeLike(itemId = itemKey, total = total)
                iterator.remove()
            }
        }
        isLiked()
    }

    private fun addLike() {
        when(val itemPull = mapLikes.checkPull()) {
            null -> {
                mapLikes["id$total"] = itemId
                total++
                database.addLike(itemId = "id${total - 1}", value = itemId,total = total)
                isLiked()
            }
            else -> {
                mapLikes[itemPull] = itemId
                total++
                database.addLike(itemId = itemPull, value = itemId, total = total)
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