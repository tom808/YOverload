package com.yoverload.network

import android.util.Log
import com.yoverload.Item
import com.yoverload.ItemReceiver
import com.yoverload.R
import com.yoverload.ifNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
/**
 * Created by tom.egan on 04-Jan-2019.
 */
class Controller {

    private lateinit var retrofit :Retrofit
    private val TAG = "Controller"


    private fun buildService(): YCombinatorService {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://hacker-news.firebaseio.com/v0/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        return retrofit.create(YCombinatorService::class.java)
    }



    fun getTopStories(receiver: ItemReceiver) {
        buildService().topStories().enqueue(object: Callback<List<Int>> {

            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                Log.e(TAG, "Failed to get list of top stories")
            }

            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                response.body()?.let {
                    val items : List<Int> = response.body()
                    items.forEach { getItem(it, receiver) }

                }
            }
        })
    }

    fun getItem(itemNum: Int, receiver: ItemReceiver) {

        buildService().getItem(itemNum).enqueue(object: Callback<Item> {
            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.e(TAG, "Failed to get item")
            }

            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                response.body()?.let { receiver.receiveItem(it) }
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
