package com.beleavemebe.androidlabentrytask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.beleavemebe.androidlabentrytask.model.User
import com.beleavemebe.androidlabentrytask.repositories.UserRepository

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository.getInstance()
    private val userEmailLiveData = MutableLiveData<String>()

    val userLiveData: LiveData<User?> =
        Transformations.switchMap(userEmailLiveData) { email ->
            userRepository.getUser(email)
        }

    fun loadUser(email: String) {
        userEmailLiveData.value = email
    }

}