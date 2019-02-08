package com.yoverload.ui

import androidx.lifecycle.ViewModel
import com.yoverload.data.ItemRepository
import com.yoverload.internal.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by tom on 14-Jan-2019.
 */
class MainPageViewModel(private val itemRepo: ItemRepository) : ViewModel() {
    val topStories by lazyDeferred {
        itemRepo.getTopStoryIds()
    }
}