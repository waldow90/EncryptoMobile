package com.example.encrypto.sql

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Room
import com.example.encrypto.AddAccount

class ManageDB {
    fun AddDefault(context: Context){
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()

        val t = Thread{
            val test = Entity()
            test.id = 1
            test.account = "Encrypto"
            test.username = "0"
            test.password = ""
            db.dao().Add(test)

            val setting1 = Entity()
            setting1.id = 5
            setting1.account = "PasswordLenght"
            setting1.username = "24"
            setting1.password = ""
            db.dao().Add(setting1)

            val setting2 = Entity()
            setting2.id = 6
            setting2.account = "Theme"
            setting2.username = "Light"
            setting2.password = ""
            db.dao().Add(setting2)

            db.dao().FirstTimeDone()
        }
        db.close()

        t.start()
        t.join()
    }

    fun CheckFirstTime(context: Context): Boolean{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var ft = ""
        val t = Thread{
            ft = db.dao().FirstTime()
        }
        t.start()
        t.join()
        db.close()

        if (ft == "1"){
            return false
        }
        return true
    }

    fun AddSecurityQuestion(context: Context, id: Int, question: String, answer: String){
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        val t = Thread {
            val q1 = Entity()
            q1.id = id
            q1.account = ("Question " + (id - 1))
            q1.username = question
            q1.password = answer
            db.dao().Add(q1)
        }
        t.start()
        t.join()
        db.close()

    }

    fun checkPin(context: Context, pin: String): Boolean{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var dbPin = ""
        val t = Thread{
            dbPin = db.dao().GetPin()
        }
        t.start()
        t.join()
        db.close()
        return Encryption().checkPin(dbPin, pin)
    }

    fun ChangePin(context: Context, pin: String){
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        val t = Thread {
            db.dao().ChangePin(Encryption().encryptPin(pin))
        }
        t.start()
        t.join()
        db.close()

    }

    fun GetAccounts (context: Context): List<String>{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var accounts: List<String> = emptyList()
        val t = Thread{
            accounts = db.dao().GetAccounts()
        }
        t.start()
        t.join()
        db.close()

        return accounts
    }

    fun AddAccount (context: Context, account: String, username: String, password: String){
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        val t = Thread{
            val new = Entity()
            var id = db.dao().LastId() + 1
            if (id < 20){
                id = 20
            }
            new.id = id
            new.account = account
            new.username = username
            new.password = password
            db.dao().Add(new)
        }
        t.start()
        t.join()
        db.close()

    }

    fun GetUsername (context: Context, account: String): String{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var username = ""
        val t = Thread{
            username = db.dao().GetUsername(account)
        }
        t.start()
        t.join()
        db.close()

        return username
    }

    fun GetPassword(context: Context, account: String): String{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var password = ""
        val t = Thread{
            password = db.dao().GetPassword(account)
        }
        t.start()
        t.join()
        db.close()

        return password
    }

    fun DeleteAccount(context: Context, account: String){
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        val t = Thread{
            db.dao().DeleteAccount(account)
        }
        t.start()
        t.join()
    }

    fun CheckSequrityQuestion(context: Context, answer: String, questionID: Int): Boolean{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var dbAnswer = ""
        val t = Thread{
            dbAnswer = db.dao().GetSecurityQuestion(questionID)
        }
        t.start()
        t.join()
        db.close()

        return dbAnswer == answer
    }

    fun GetSequrityQuestion(context: Context, questionID: Int): String{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var dbQuestion = ""
        val t = Thread{
            dbQuestion = db.dao().GetSecurityQuestion(questionID)
        }
        t.start()
        t.join()
        db.close()

        return dbQuestion
    }

    fun SetSetting(context: Context, setting: String, value: String){
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        val t = Thread{
            db.dao().SetSetting(setting, value)
        }
        t.start()
        t.join()
        db.close()

    }

    fun GetSetting(context: Context, setting: String): String{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var value = ""
        val t = Thread{
            value = db.dao().GetSetting(setting)
        }
        t.start()
        t.join()
        db.close()

        return value
    }
}