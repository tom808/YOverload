package com.yoverload.network

import androidx.lifecycle.LiveData
import com.yoverload.data.db.Item

interface YCNetworkDataSource {

    val downloadedTopStoryIds : LiveData<List<Int>>
    val downloadedItemData : LiveData<List<Item>>

    fun getTopStoryIds()

    fun getItems(itemIds: List<Int>)

}
