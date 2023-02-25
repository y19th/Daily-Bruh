package com.example.dailybruh.callback

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dailybruh.interfaces.ItemTouchHelperAdapter

class RecyclerItemTouchHelper(adapter: ItemTouchHelperAdapter) : ItemTouchHelper.Callback() {

    private lateinit var _adapter: ItemTouchHelperAdapter

    init {
        _adapter = adapter
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swipeFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE,swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when(direction) {
            ItemTouchHelper.UP -> _adapter.onItemMove(0,1)
            ItemTouchHelper.DOWN -> _adapter.onItemMove(1,0)
        }

    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

}