package com.yoverload

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by tom.egan on 03-Jan-2019.
 */
interface YCombinatorService {
    @GET("maxitem.json?print=pretty")
    fun getLatest(): Call<Int>

    @GET("item/{item}.json?print=pretty")
    fun listRepos(@Path("item") item: String): Call<List<Item>>
}