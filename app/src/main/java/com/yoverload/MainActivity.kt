package com.yoverload

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        tvMaxItem = findViewById(R.id.maxItem)
        callService()
    }

    fun callService() {
        val retrofit = Retrofit.Builder()
                .baseUrl(" https://htvMaxItemacker-news.firebaseio.com/v0/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val service = retrofit.create(YCombinatorService::class.java)
        val call = service.getLatest()
        call.execute()
    }


    override fun onResponse(call: Call<Int>, response: Response<Int>) {
        response.body()?.let {
            tvMaxItem.setText(it)
        }
    }

    override fun onFailure(call: Call<Int>, t: Throwable) {
        tvMaxItem.setText(getString(R.string.failed))
    }
}