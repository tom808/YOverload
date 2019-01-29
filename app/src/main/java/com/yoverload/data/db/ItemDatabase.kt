package com.yoverload.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

/**
 * Created by tom on 14-Jan-2019.
 */

@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: ItemDatabase? = null
        private const val DATABASE_NAME : String = "YOverLoad.db"

        fun getInstance(context: Context) : ItemDatabase {
            instance ?: synchronized(this) {
                instance
                        ?: Room.databaseBuilder(context, ItemDatabase::class.java, DATABASE_NAME).build()
            }

            return instance!!
        }
    }

    abstract fun itemDao() : ItemDao
}