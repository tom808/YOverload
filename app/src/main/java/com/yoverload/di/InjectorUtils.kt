package com.yoverload.di

import android.content.Context
import com.yoverload.data.ItemRepository
import com.yoverload.data.db.ItemDatabase
import com.yoverload.network.YCNetworkDataSourceImpl
import com.yoverload.network.YCombinatorApi

/**
 * Created by tom on 18-Jan-2019.
 */
object InjectorUtils {

    fun provideMainPageViewModelFactory(context: Context): MainPageViewModelFactory {
        val YCNetworkDataSource = YCNetworkDataSourceImpl(YCombinatorApi())
        val db = ItemDatabase.getInstance(context)
        val repository = ItemRepository.getInstance(YCNetworkDataSource, db)
        return MainPageViewModelFactory(repository)
    }

}