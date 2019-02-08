package com.yoverload.network

import androidx.lifecycle.LiveData
import com.yoverload.data.db.Item

interface YCNetworkDataSource {

    val downloadedTopStoryIds : LiveData<List<Int>>
    val downloadedItemData : LiveData<List<Item>>

    suspend fun getTopStoryIds()

    suspend fun getItems(itemIds: List<Int>)

}
