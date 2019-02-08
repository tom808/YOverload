package com.yoverload.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yoverload.data.db.Item
import com.yoverload.internal.NoConnectivityException

/**
 * Created by tom on 07-Feb-2019.
 */
class YCNetworkDataSourceImpl(val api: YCombinatorApi) : YCNetworkDataSource {

    private val _downloadedTopStoryIds = MutableLiveData<List<Int>>()
    override val downloadedTopStoryIds: LiveData<List<Int>>
        get() = _downloadedTopStoryIds

    private val _downloadedItemData = MutableLiveData<List<Item>>()
    override val downloadedItemData: LiveData<List<Item>>
        get() = _downloadedItemData

    override suspend fun getTopStoryIds() {
        try {
            val topStoryIds = api.topStories().await()
            _downloadedTopStoryIds.postValue(topStoryIds)
        } catch (e : NoConnectivityException) {
            Log.e("getTopStoryIds", "Network Error")
        }
    }

    override suspend fun getItems(itemIds: List<Int>) {
        val collectedItems = mutableListOf<Item>()
        try {
            itemIds.forEach {
                collectedItems.add(getItem(it))
            }
            _downloadedItemData.postValue(collectedItems)
        } catch (e : NoConnectivityException) {
            Log.e("getItems", "Network Error")
        }
    }

    private suspend fun getItem(item: Int): Item = api.getItem(item).await()
}