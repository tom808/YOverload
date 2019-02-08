package com.yoverload.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yoverload.data.db.Item
import com.yoverload.data.db.ItemDatabase
import com.yoverload.network.YCNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by tom.egan on 04-Jan-2019.
 */
class ItemRepository private constructor(private val networkData: YCNetworkDataSource, private val itemDatabase: ItemDatabase) {

    private val dataCache = MutableLiveData<MutableList<Item>>()

    init {
        dataCache.value = mutableListOf()
        networkData.apply {
            downloadedTopStoryIds.observeForever {
                calcMissingItemIds(it)
            }
            downloadedItemData.observeForever {
                insertItems(it)
            }
        }
    }

    private fun insertItems(items: List<Item>) {
        GlobalScope.launch(Dispatchers.IO) {
            itemDatabase.itemDao().insertAll(items)
        }
    }

    companion object {
        @Volatile
        private var instance: ItemRepository? = null

        fun getInstance(networkData: YCNetworkDataSource, database: ItemDatabase) =
                instance ?: synchronized(this) {
                    instance
                            ?: ItemRepository(networkData, database).also { instance = it }
                }
    }

    private suspend fun updateTopStories() {
        networkData.getTopStoryIds()
    }

    suspend fun getTopStoryIds() : LiveData<List<Item>> {
        return withContext(Dispatchers.IO) {
            updateTopStories()
            return@withContext itemDatabase.itemDao().loadAll()
        }
    }

    private fun calcMissingItemIds(items: List<Int>) {
        GlobalScope.launch(Dispatchers.IO) {
            val itemsInDb = itemDatabase.itemDao().loadSelected(items)
            val itemsIds = itemsInDb.map { it.id }
            val missingItemIds = items.minus(itemsIds)
            if (missingItemIds.size > 10) {
                networkData.getItems(missingItemIds.subList(0,10))
            }
        }
    }

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
