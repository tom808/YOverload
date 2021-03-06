package com.yoverload.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query


/**
 * Created by tom on 14-Jan-2019.
 */
@Dao
interface ItemDao {
    @Insert(onConflict = REPLACE)
    fun save(item: Item)

    @Query("SELECT * FROM items")
    fun loadAll(): LiveData<List<Item>>

    @Query("Select * FROM items WHERE id IN (:items)")
    fun loadSelected(items: List<Int>) : List<Item>

    @Insert(onConflict = REPLACE)
    fun insertAll(items: List<Item>?)
}
