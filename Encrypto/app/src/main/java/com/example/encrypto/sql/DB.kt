package com.example.encrypto.sql

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities = [(com.example.encrypto.sql.Entity::class)], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun dao(): DAO
}