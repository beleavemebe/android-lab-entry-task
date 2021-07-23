package com.beleavemebe.androidlabentrytask.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.beleavemebe.androidlabentrytask.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}