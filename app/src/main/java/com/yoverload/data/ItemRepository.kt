package com.yoverload.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.yoverload.data.db.Item
import com.yoverload.data.db.ItemDatabase
import com.yoverload.network.YCNetworkDataSource

/**
 * Created by tom.egan on 04-Jan-2019.
 */
class ItemRepository private constructor(context: Context, val networkData: YCNetworkDataSource, val itemDatabase: ItemDatabase) {

    private val TAG = "ItemRepository"

    private val dataCache = MutableLiveData<MutableList<Item>>()

    init {
        dataCache.value = mutableListOf()
        networkData.apply {
            downloadedTopStoryIds.observeForever {
//                calcMissingItemIds(it)
            }
         }
    }

    companion object {
        @Volatile
        private var instance: ItemRepository? = null

        fun getInstance(context: Context, networkData: YCNetworkDataSource, database: ItemDatabase) =
                instance ?: synchronized(this) {
                    instance ?: ItemRepository(context, networkData, database).also { instance = it }
                }
    }

    fun getTopStoryIds() {
        networkData.getTopStoryIds()
    }

//    fun calcMissingItemIds(items : List<Int>) {
//        doAsync {
//            val itemsInDb = itemDatabase.itemDao().loadSelected(items)
//            val itemsIds = itemsInDb.map { it.id }
//            val missingItemIds = items.minus(itemsIds)
//            if (missingItemIds.isNotEmpty()) {
//                networkData.getItems(missingItemIds)
//            }
//        }
//    }

//    fun getTopStories(): MutableLiveData<MutableList<Item>> {
//
//        // TODO this should come from a DAO and be injected in!
//         .topStories().enqueue(object : Callback<List<Int>> {
//
//            override fun onFailure(call: Call<List<Int>>?, t: Throwable) {
//                Log.e(TAG, "Failed to get list of top stories")
//            }
//
//            override fun onResponse(call: Call<List<Int>>?, response: Response<List<Int>>) {
//                val items: List<Int> = response.body()!!
//                items.forEach { getItem(it) }
//            }
//        })
//
//        return dataCache
//    }

//    fun getItem(itemNum: Int) {
//
//        YCombinatorApi().getItem(itemNum).enqueue(object : Callback<Item> {
//            override fun onFailure(call: Call<Item>, t: Throwable) {
//                Log.e(TAG, "Failed to get item")
//            }
//
//            override fun onResponse(call: Call<Item>, response: Response<Item>) {
//                response.body()?.let {
//                    doAsync {
//                        itemDb.itemDao().save(it)
//                    }
//                    dataCache.value?.add(it)
//                    dataCache.value = dataCache.value
//                }
//            }
//        })
//    }


}
