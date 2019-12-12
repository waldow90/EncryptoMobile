package com.example.encrypto.sql

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface DAO {

    @Insert(onConflict = REPLACE)
    fun Add(add: Entity)

    @Query("SELECT id FROM DB WHERE id = (SELECT MAX(id) FROM DB)")
    fun LastId(): Int

    @Query("INSERT INTO DB (id, account, username, password) VALUES (:id, :account, :username, :password)")
    fun NewAccount(id: Int, account: String, username: String, password: String)

    @Query("SELECT account FROM DB WHERE id >= 20")
    fun GetAccounts(): List<String>

    @Query("SELECT account FROM DB WHERE id>= 20 AND account LIKE :input")
    fun GetAccounts(input: String): List<String>

    @Query("UPDATE DB SET password = :PIN WHERE id = 1")
    fun ChangePin(PIN: String)

    @Query("SELECT password FROM DB WHERE id = 1")
    fun GetPin(): String

    @Query("SELECT password FROM DB WHERE account = :account")
    fun GetPassword(account: String): String

    @Query("SELECT username FROM DB WHERE account = :account")
    fun GetUsername(account: String): String

    @Query("DELETE FROM DB WHERE account = :account")
    fun DeleteAccount(account: String)

    @Query("SELECT account FROM DB WHERE account = :account")
    fun SearchAccount(account: String): String

    @Query("SELECT username FROM DB WHERE account = :account")
    fun GetSetting(account: String): String

    @Query("UPDATE DB SET username = :value WHERE account = :setting")
    fun SetSetting(setting: String, value: String)

    @Query("SELECT username FROM DB WHERE id = 1")
    fun FirstTime(): String

    @Query("UPDATE DB SET username = 1 WHERE id = 1")
    fun FirstTimeDone()

    @Query("SELECT username FROM DB WHERE id = :id")
    fun GetSecurityQuestion(id: Int): String
}