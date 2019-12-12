package com.example.encrypto.sql

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface DAO {

    @Insert(onConflict = REPLACE)
    fun add(add: Entity)

    @Query("SELECT id FROM DB WHERE id = (SELECT MAX(id) FROM DB)")
    fun lastId(): Int

    //@Query("INSERT INTO DB (id, account, username, password) VALUES (:id, :account, :username, :password)")
    //fun NewAccount(id: Int, account: String, username: String, password: ByteArray)

    @Query("SELECT account FROM DB WHERE id >= 20")
    fun getAccounts(): List<String>

    @Query("SELECT account FROM DB WHERE id>= 20 AND account LIKE :input")
    fun searchAccounts(input: String): List<String>

    @Query("UPDATE DB SET username = :PIN WHERE id = 1")
    fun changePin(PIN: String)

    @Query("SELECT username FROM DB WHERE id = 1")
    fun getPin(): String

    @Query("SELECT password FROM DB WHERE account = :account")
    fun getPassword(account: String): ByteArray

    @Query("SELECT username FROM DB WHERE account = :account")
    fun getUsername(account: String): String

    @Query("DELETE FROM DB WHERE account = :account")
    fun deleteAccount(account: String)

    @Query("SELECT account FROM DB WHERE id = 1")
    fun firstTime(): String

    //@Query("UPDATE DB SET username = 1 WHERE id = 1")
    //fun FirstTimeDone()

    @Query("SELECT account FROM DB WHERE id = :id")
    fun getSecurityQuestion(id: Int): String

    @Query("SELECT username FROM DB WHERE id = :id")
    fun checkSecurityQuestion(id: Int): String
}