package com.beleavemebe.androidlabentrytask.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.beleavemebe.androidlabentrytask.database.UserDatabase
import com.beleavemebe.androidlabentrytask.model.User
import java.lang.IllegalStateException
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class UserRepository private constructor(context: Context) {
    companion object {
        private var INSTANCE: UserRepository? = null
        private const val DATABASE_NAME = "user-database"

        fun initialize(context: Context) {
            if (INSTANCE == null) INSTANCE = UserRepository(context)
        }

        fun getInstance(): UserRepository {
            return INSTANCE ?: throw IllegalStateException("Repo was not initialized")
        }
    }

    private val database: UserDatabase = Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val userDao = database.userDao()
    private val executor: Executor = Executors.newSingleThreadExecutor()

    fun getUser(email: String): LiveData<User?> = userDao.getUser(email)

    fun addUser(user: User) {
        executor.execute {
            userDao.addUser(user)
        }
    }
}