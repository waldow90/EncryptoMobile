package com.example.encrypto.sql

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Room
import com.example.encrypto.AddAccount

class ManageDB {
    fun addDefault(context: Context){
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

    fun checkFirstTime(context: Context): Boolean{
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

    fun addSecurityQuestion(context: Context, id: Int, question: String, answer: String){
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

    fun changePin(context: Context, pin: String){
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

    fun getAccounts (context: Context): List<String>{
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

    fun addAccount (context: Context, account: String, username: String, password: String){
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
            new.username = Encryption().encrypt(username, db.dao().GetPin())
            new.password = Encryption().encrypt(password, db.dao().GetPin())
            db.dao().Add(new)
        }
        t.start()
        t.join()
        db.close()

    }

    fun getUsername (context: Context, account: String): String{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var username = ""
        val t = Thread{
            username = Encryption().decrypt(db.dao().GetUsername(account), db.dao().GetPin())

        }
        t.start()
        t.join()
        db.close()

        return username
    }

    fun getPassword(context: Context, account: String): String{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var password = ""
        val t = Thread{
            password = Encryption().decrypt(db.dao().GetPassword(account), db.dao().GetPin())
        }
        t.start()
        t.join()
        db.close()

        return password
    }

    fun deleteAccount(context: Context, account: String){
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

    fun checkSequrityQuestion(context: Context, answer: String, questionID: Int): Boolean{
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

    fun getSequrityQuestion(context: Context, questionID: Int): String{
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
}