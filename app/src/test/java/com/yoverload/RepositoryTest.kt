package com.yoverload

import com.yoverload.data.ItemRepository
import org.junit.Before
import org.junit.Test

/**
 * Created by tom on 08-Feb-2019.
 */
class RepositoryTest {

    lateinit var repo :ItemRepository
    @Before
    fun setUp() {

        repo = ItemRepository()
    }

    @Test
    fun getTopStoryIds() {

    }

}