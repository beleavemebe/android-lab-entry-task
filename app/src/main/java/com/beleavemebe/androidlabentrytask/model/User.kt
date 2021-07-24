package com.beleavemebe.androidlabentrytask.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey val email: String,
    val password: String,
    val name: String,
    val surname: String,
) {
    companion object {
        val USER_NONE = User("", "", "", "")
    }
}