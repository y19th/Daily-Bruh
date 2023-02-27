package com.example.dailybruh.manager

import android.content.Context
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class LinearRecyclerManager(con: Context): LinearLayoutManager(con) {

    private val context: Context

    init {
        context = con
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?,
        state: RecyclerView.State?,
        position: Int
    ) {
        val lin = object : LinearSmoothScroller(context) {
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
                return 0.1f
            }
        }
        lin.targetPosition = position
        startSmoothScroll(lin)
    }

    override fun isSmoothScrolling(): Boolean {
        return true
    }

   /* override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        return if(dy > 50) {
            super.scrollVerticallyBy(dy, recycler, state)
        }
        else if(dy < -50) super.scrollVerticallyBy(dy, recycler, state)
        else return 0

    }*/
}