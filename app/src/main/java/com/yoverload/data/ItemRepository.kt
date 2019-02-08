package com.yoverload.data

import androidx.lifecycle.LiveData
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

    init {
        networkData.apply {
            downloadedTopStoryIds.observeForever {
                calcMissingItemIds(it)
            }
            downloadedItemData.observeForever {
                insertItems(it)
            }
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

    private fun insertItems(items: List<Item>) {
        GlobalScope.launch(Dispatchers.IO) {
            itemDatabase.itemDao().insertAll(items)
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
            if (missingItemIds.isNotEmpty()) {
                networkData.getItems(missingItemIds.subList(0,10))
            }
        }
    }
}
