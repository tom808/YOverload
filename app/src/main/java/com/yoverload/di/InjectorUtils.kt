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
        val networkDataSource = YCNetworkDataSourceImpl(YCombinatorApi(context))
        val db = ItemDatabase.getInstance(context)
        val repository = ItemRepository.getInstance(networkDataSource, db)
        return MainPageViewModelFactory(repository)
    }

}