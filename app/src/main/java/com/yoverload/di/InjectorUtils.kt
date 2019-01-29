package com.yoverload.di

import android.content.Context
import com.yoverload.data.ItemRepository

/**
 * Created by tom on 18-Jan-2019.
 */
object InjectorUtils {

    fun provideMainPageViewModelFactory(context: Context): MainPageViewModelFactory {
        val repository = ItemRepository.getInstance(context)
        return MainPageViewModelFactory(repository)
    }

}