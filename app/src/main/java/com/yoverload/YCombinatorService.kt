package com.yoverload

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by tom.egan on 03-Jan-2019.
 */
interface YCombinatorService {
    // The current largest item id is at /v0/maxitem. You can walk backward from here to discover all items.
    @GET("maxitem.json")
    fun getLatest(@Query("print") print: String = "pretty"): Call<Int>

    // Stories, comments, jobs, Ask HNs and even polls are just items.
    // They're identified by their ids, which are unique integers, and live under /v0/item/<id>
    @GET("item/{item}.json")
    fun listRepos(@Path("item") item: String, @Query("print") print: String = "pretty"): Call<List<Item>>
}