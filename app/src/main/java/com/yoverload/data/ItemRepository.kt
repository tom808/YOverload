package com.yoverload.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import android.util.Log
import com.yoverload.data.db.Item
import com.yoverload.data.db.ItemDatabase
import com.yoverload.network.YCombinatorService
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by tom.egan on 04-Jan-2019.
 */
class ItemRepository private constructor(context: Context) {

    private val TAG = "ItemRepository"

    private val itemDb : ItemDatabase = ItemDatabase.getInstance(context)

    private val dataCache = MutableLiveData<MutableList<Item>>()

    init {
        dataCache.value = mutableListOf()
    }

    companion object {
        @Volatile
        private var instance: ItemRepository? = null

        fun getInstance(context: Context) =
                instance ?: synchronized(this) {
                    instance
                            ?: ItemRepository(context).also { instance = it }
                }
    }


    fun getTopStories(): MutableLiveData<MutableList<Item>> {

        // TODO this should come from a DAO and be injected in!
        YCombinatorService().topStories().enqueue(object : Callback<List<Int>> {

            override fun onFailure(call: Call<List<Int>>?, t: Throwable) {
                Log.e(TAG, "Failed to get list of top stories")
            }

            override fun onResponse(call: Call<List<Int>>?, response: Response<List<Int>>) {
                val items: List<Int> = response.body()!!
                items.forEach { getItem(it) }
            }
        })

        return dataCache
    }

    fun getItem(itemNum: Int) {

        YCombinatorService().getItem(itemNum).enqueue(object : Callback<Item> {
            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.e(TAG, "Failed to get item")
            }

            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                response.body()?.let {
                    doAsync {
                        itemDb.itemDao().save(it)
                    }
                    dataCache.value?.add(it)
                    dataCache.value = dataCache.value
                }
            }
        })
    }


}
