package com.example.encrypto.sql

import android.content.Context
import androidx.room.Room

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
            test.account = "1"
            test.username = ""
            test.password = null
            db.dao().add(test)

        }
        t.start()
        t.join()
        db.close()
    }

    fun checkFirstTime(context: Context): Boolean{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var ft = ""
        val t = Thread{
            ft = db.dao().firstTime()
        }
        t.start()
        t.join()
        db.close()

        return (ft != "1")
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
            q1.account = question
            q1.username = Encryption().encryptPin(answer)
            q1.password = null
            db.dao().add(q1)
        }
        t.start()
        t.join()
        db.close()
    }

    fun checkPin(context: Context, inputPin: String): Boolean{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var correct = false
        val t = Thread{
            correct = Encryption().checkPin(db.dao().getPin(), inputPin)
        }
        t.start()
        t.join()
        db.close()
        return correct
    }

    fun changePin(context: Context, inputPin: String){
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        val t = Thread {
            db.dao().changePin(Encryption().encryptPin(inputPin))
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
            accounts = db.dao().getAccounts()
        }
        t.start()
        t.join()
        db.close()

        return accounts
    }

    fun getAccounts (context: Context, input: String): List<String>{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var accounts: List<String> = emptyList()
        val t = Thread{
            accounts = db.dao().searchAccounts(input)
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
            var id = db.dao().lastId() + 1
            if (id < 20){
                id = 20
            }
            new.id = id
            new.account = account
            new.username = username
            new.password = Encryption().encrypt(password, db.dao().getPin())
            db.dao().add(new)
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
            username = db.dao().getUsername(account)
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
            password = Encryption().decrypt(db.dao().getPassword(account), db.dao().getPin())
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
            db.dao().deleteAccount(account)
        }
        t.start()
        t.join()
        db.close()
    }

    fun checkSecurityQuestion(context: Context, answer: String, questionID: Int): Boolean{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var dbAnswer = ""
        val t = Thread{
            dbAnswer = db.dao().checkSecurityQuestion(questionID)
        }
        t.start()
        t.join()
        db.close()

        return dbAnswer == answer
    }

    fun getSecurityQuestion(context: Context, questionID: Int): String{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var dbQuestion = ""
        val t = Thread{
            dbQuestion = db.dao().getSecurityQuestion(questionID)
        }
        t.start()
        t.join()
        db.close()

        return dbQuestion
    }
}