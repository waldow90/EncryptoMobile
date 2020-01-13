package com.example.encrypto.classes

import android.content.Context
import androidx.room.Room
import com.example.encrypto.sql.Database
import com.example.encrypto.sql.Entity

class ManageDB {
    
    private val encryption = Encryption()

    //check if this is first time
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

    //add default value for id = 1
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

    //change pin (first time)
    fun setupPin(context: Context, inputPin: String){
        val db = Room.databaseBuilder(context, Database::class.java, "DB").fallbackToDestructiveMigration().build()

        val t = Thread{
            db.dao().changePin(encryption.encryptPin(inputPin)) //encrypt and save pin to database
        }
        t.start()
        t.join()
        db.close()
    }

    //change pin
    fun changePin(context: Context, inputPin: String){
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()

        val t = Thread {
            val oldpin = db.dao().getPin() //get the old pin from database
            val newpin = encryption.encryptPin(inputPin) //encrypt the new pin

            db.dao().selectAllAccounts().forEach{ //go throught each of the accounts, decrypt each password with old pin, and encrypt it with new pin
                val newacc = Entity()
                newacc.id = it.id
                newacc.account = it.account
                newacc.username = it.username
                newacc.password = encryption.encrypt(encryption.decrypt(it.password!!, oldpin), newpin)
                db.dao().add(newacc)
            }

            db.dao().changePin(newpin) //save new pin
        }
        t.start()
        t.join()
        db.close()
    }

    //check if pin is correct
    fun checkPin(context: Context, inputPin: String): Boolean{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()

        var correct = false
        val t = Thread{
            correct = encryption.checkPin(db.dao().getPin(), inputPin)
        }
        t.start()
        t.join()
        db.close()
        return correct
    }

    //add new account to database
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
            new.password = encryption.encrypt(password, db.dao().getPin())
            db.dao().add(new)
        }
        t.start()
        t.join()
        db.close()
    }

    //get a list of all accounts
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

    //get a list of accounts matching the input
    fun searchAccounts (context: Context, input: String): List<String>{
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

    //delete account
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

    //get username from database
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

    //get password from database
    fun getPassword(context: Context, account: String): String{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()

        var password = ""
        val t = Thread{
            password = encryption
                .decrypt(db.dao().getPassword(account), db.dao().getPin())
        }
        t.start()
        t.join()
        db.close()
        return password
    }

    //add a security question
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
            q1.username = encryption.encryptPin(answer)
            q1.password = null
            db.dao().add(q1)
        }
        t.start()
        t.join()
        db.close()
    }

    //get security question from database
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

    //check if answer to security question is correct
    fun checkSecurityQuestion(context: Context, answer: String, questionID: Int): Boolean{
        val db = Room.databaseBuilder(
            context,
            Database::class.java,
            "DB"
        ).fallbackToDestructiveMigration().build()
        var dbAnswer = ""
        val t = Thread{
            dbAnswer = db.dao().checkSecurityAnswer(questionID)
        }
        t.start()
        t.join()
        db.close()

        return dbAnswer == answer
    }
}