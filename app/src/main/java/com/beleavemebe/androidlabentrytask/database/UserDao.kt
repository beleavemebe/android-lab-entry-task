package com.beleavemebe.androidlabentrytask.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.beleavemebe.androidlabentrytask.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE email=(:email)")
    fun getUser(email: String): LiveData<User?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)

}