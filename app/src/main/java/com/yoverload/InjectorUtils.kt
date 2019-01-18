package com.yoverload

/**
 * Created by tom on 18-Jan-2019.
 */
object InjectorUtils {

    fun provideMainPageViewModelFactory(): MainPageViewModelFactory {
        val repository = ItemRepository.getInstance()
        return MainPageViewModelFactory(repository)
    }

}