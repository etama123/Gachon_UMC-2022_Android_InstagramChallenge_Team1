package com.example.InstagramClone

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewLoadMoreScroll: RecyclerView.OnScrollListener {
    private var visibleThreshold = 8
    private lateinit var mOnLoadMoreListener: OnLoadMoreListener
    private var isLoading: Boolean = false
    private var lastVisibleItem:Int = 0
    private var totalItemCount: Int = 0
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    fun setLoaded(){
        isLoading = false
    }

    fun getLoaded(): Boolean{
        return isLoading
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener){
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    constructor(layoutManager: GridLayoutManager){
        this.mLayoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if(dy<=0) return

        totalItemCount = mLayoutManager.itemCount

        if(mLayoutManager is GridLayoutManager){
            lastVisibleItem = (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
        }

        if(!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold){
            mOnLoadMoreListener.onLoadMore()
            isLoading = true
        }

    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray):Int{
        var maxSize = 0
        for(i in lastVisibleItemPositions){
            if(i==0){
                maxSize = lastVisibleItemPositions[i]
            }else if(lastVisibleItemPositions[i] > maxSize){
                maxSize = lastVisibleItemPositions[i]
            }
        }

        return maxSize
    }
}