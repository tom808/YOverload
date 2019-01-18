package com.yoverload.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.yoverload.data.ItemRepository
import com.yoverload.ui.MainPageViewModel

class MainPageViewModelFactory(private val repository: ItemRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainPageViewModel(repository) as T
    }
}
