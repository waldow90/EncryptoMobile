package com.example.encrypto.sql

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DB")
class Entity {
    @PrimaryKey
    var id: Int = 0

    @ColumnInfo
    var account: String = ""

    @ColumnInfo
    var username: String = ""

    @ColumnInfo
    var password: ByteArray? = null
}