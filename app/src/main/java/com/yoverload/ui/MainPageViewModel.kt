package com.yoverload.ui

import android.arch.lifecycle.ViewModel
import com.yoverload.data.ItemRepository

/**
 * Created by tom on 14-Jan-2019.
 */
class MainPageViewModel(private val itemRepo: ItemRepository) : ViewModel() {

    fun getTopStories() = itemRepo.getTopStories()
}