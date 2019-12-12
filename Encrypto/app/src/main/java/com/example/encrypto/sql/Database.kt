package com.example.encrypto.sql

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities = [(Entity::class)], version = 2, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun dao(): DAO
}