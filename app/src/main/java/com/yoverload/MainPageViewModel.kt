package com.yoverload

import android.arch.lifecycle.ViewModel

/**
 * Created by tom on 14-Jan-2019.
 */
class MainPageViewModel(private val itemRepo: ItemRepository) : ViewModel() {

    fun getTopStories() = itemRepo.getTopStories()
}