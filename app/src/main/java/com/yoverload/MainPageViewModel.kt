package com.yoverload

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.yoverload.network.Item

/**
 * Created by tom on 14-Jan-2019.
 */
class MainPageViewModel : ViewModel() {
    private var itemId: String? = null
    private var items: MutableLiveData<MutableList<Item>>? = null
    private val repo: ItemRepository = ItemRepository() // TODO inject this in!


    fun init(items: MutableLiveData<MutableList<Item>>) {
        this.items?:let {
            repo.getTopStories()
        }
    }
}