package com.yoverload.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//import com.squareup.moshi.Json

@Entity(tableName = "items")
data class Item(
        @PrimaryKey() val id: Int,
//        @Json(name="by")
        @ColumnInfo(name="author") val by: String,
        @ColumnInfo(name="descendants") val descendants: Int,
        //@ColumnInfo(name="kids") val kids: List<Int>,
        @ColumnInfo(name="score") val score: Int,
        @ColumnInfo(name="time") val time: Int,
        @ColumnInfo(name="title") val title: String,
        @ColumnInfo(name="type") val type: String,
        @ColumnInfo(name="url") val url: String?
)

