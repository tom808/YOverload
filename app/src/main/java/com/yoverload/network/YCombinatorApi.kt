package com.yoverload.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.yoverload.data.db.Item
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by tom.egan on 03-Jan-2019.
 */
interface YCombinatorApi {

    companion object {
        private const val BASE_URL: String = "https://hacker-news.firebaseio.com/v0/"

        operator fun invoke(): YCombinatorApi {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                    .create(YCombinatorApi::class.java)
        }
    }

    // The current largest item id is at /v0/maxitem. You can walk backward from here to discover all items.
    @GET("maxitem.json")
    fun getLatest(@Query("print") print: String = "pretty"): Deferred<Int>

    // Get up to 500 top stories
    @GET("topstories.json")
    fun topStories(@Query("print") print: String = "pretty"): Deferred<List<Int>>

    // Stories, comments, jobs, Ask HNs and even polls are just items.
    // They're identified by their ids, which are unique integers, and live under /v0/item/<id>
    @GET("item/{item}.json")
    fun getItem(@Path("item") item: Int, @Query("print") print: String = "pretty"): Deferred<Item>
}