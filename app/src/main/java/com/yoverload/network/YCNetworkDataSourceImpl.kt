package com.yoverload.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yoverload.data.db.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    private val collectedItems = mutableListOf<Item>()

    override fun getTopStoryIds() {
        api.topStories().enqueue(object : Callback<List<Int>> {
            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                Log.e("getTopStoryIds", "Failed to get list of top stories")
            }

            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                response.body()?.let {
                    _downloadedTopStoryIds.postValue(it)
                }
            }
        })
    }

    override fun getItems(itemIds: List<Int>) {
        collectedItems.clear()
        itemIds.forEach { getItem(it) }
        _downloadedItemData.postValue(collectedItems)
    }

    private fun getItem(item: Int) {
        api.getItem(item).enqueue(object : Callback<Item> {
            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.e("getItem", "Failed to get item")
            }

            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                response.body()?.let {
                    collectedItems.add(it)
                }
            }
        })
    }
}