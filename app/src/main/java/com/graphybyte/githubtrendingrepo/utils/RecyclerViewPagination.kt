package com.graphybyte.githubtrendingrepo.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE

abstract class RecyclerViewPagination(recyclerView: RecyclerView) : RecyclerView.OnScrollListener() {

    private val threshold = 2
    private var endWithAuto = false
    private var recyclerView: RecyclerView? = recyclerView
    private var layoutManager: RecyclerView.LayoutManager?

    init {
        recyclerView.addOnScrollListener(this)
        layoutManager = recyclerView.layoutManager
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == SCROLL_STATE_IDLE) {
            val visibleItemCount = layoutManager!!.childCount
            val totalItemCount = layoutManager!!.itemCount
            var firstVisibleItemPosition = 0
            if (layoutManager is LinearLayoutManager) {
                firstVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            } else if (layoutManager is GridLayoutManager) {
                firstVisibleItemPosition = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            }
            if (isLastPage()) return
            if (visibleItemCount + firstVisibleItemPosition + threshold >= totalItemCount) {
                loadMore()
            }
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
    }

    abstract fun isLastPage(): Boolean
    abstract fun loadMore()
}