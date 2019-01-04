package com.yoverload.network

import android.util.Log
import com.yoverload.Item
import com.yoverload.ItemReceiver
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by tom.egan on 04-Jan-2019.
 */
class Controller {

    private val TAG = "Controller"


    fun getTopStories(receiver: ItemReceiver) {

        val instance = YCombinatorService.getInstance()

        instance?.topStories()?.enqueue(object : Callback<List<Int>> {

            override fun onFailure(call: Call<List<Int>>?, t: Throwable) {
                Log.e(TAG, "Failed to get list of top stories")
            }

            override fun onResponse(call: Call<List<Int>>?, response: Response<List<Int>>) {
                val items: List<Int> = response.body()!!
                items.forEach { getItem(it, receiver) }

            }
        })
    }

    fun getItem(itemNum: Int, receiver: ItemReceiver) {

        YCombinatorService.getInstance()?.getItem(itemNum)?.enqueue(object : Callback<Item> {
            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.e(TAG, "Failed to get item")
            }

            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                val item: Item = response.body()!!
                receiver.receiveItem(item)
            }
        })
    }

    /*

    override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
        response.body()?.let {
            val body = response.body()
            tvMaxItem.setText(body.toString())
        }
    }

    override fun onFailure(call: Call<List<Int>>, t: Throwable) {
        tvMaxItem.setText(getString(R.string.failed))
    }

    */

}