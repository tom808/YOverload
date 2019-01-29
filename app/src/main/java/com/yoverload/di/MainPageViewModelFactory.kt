package com.yoverload.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yoverload.data.ItemRepository
import com.yoverload.ui.MainPageViewModel

class MainPageViewModelFactory(private val repository: ItemRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainPageViewModel(repository) as T
    }
}
