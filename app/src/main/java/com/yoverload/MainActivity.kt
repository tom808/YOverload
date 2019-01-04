package com.yoverload

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by tom.egan on 03-Jan-2019.
 */
class MainActivity() : AppCompatActivity(), Callback<Int> {

    private lateinit var tvMaxItem: TextView
    private val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        tvMaxItem = findViewById(R.id.maxItem)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_refresh) {
            callService()
        }
        return true
    }

    fun callService() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://hacker-news.firebaseio.com/v0/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val service = retrofit.create(YCombinatorService::class.java)
        val call = service.getLatest()
        call.enqueue(this)
    }


    override fun onResponse(call: Call<Int>, response: Response<Int>) {
        Log.i(TAG, "onResponse called")
        response.body()?.let {
            val body = response.body()
            tvMaxItem.setText(body.toString())
        }
    }

    override fun onFailure(call: Call<Int>, t: Throwable) {
        Log.i(TAG, "onFailure called")
        tvMaxItem.setText(getString(R.string.failed))
    }
}