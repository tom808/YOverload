package com.yoverload

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.yoverload.network.Item
import com.yoverload.network.YCombinatorService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by tom.egan on 04-Jan-2019.
 */
class ItemRepository {

    private val TAG = "ItemRepository"


    fun getTopStories() : LiveData<MutableList<Item>> {

        val instance = YCombinatorService.getInstance()
        val data = MutableLiveData<MutableList<Item>>()

        instance?.topStories()?.enqueue(object : Callback<List<Int>> {

            override fun onFailure(call: Call<List<Int>>?, t: Throwable) {
                Log.e(TAG, "Failed to get list of top stories")
            }

            override fun onResponse(call: Call<List<Int>>?, response: Response<List<Int>>) {
                val items: List<Int> = response.body()!!
                items.forEach { getItem(it, data) }

            }
        })

        return data
    }

    fun getItem(itemNum: Int, data: MutableLiveData<MutableList<Item>>) {

        YCombinatorService.getInstance()?.getItem(itemNum)?.enqueue(object : Callback<Item> {
            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.e(TAG, "Failed to get item")
            }

            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                val item: Item = response.body()!!
                data.value?.add(item)
            }
        })
    }


}
