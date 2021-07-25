package com.beleavemebe.androidlabentrytask.ui.login

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var isUserLoggedIn : Boolean = false
    var currUserEmail: String = ""
    var currUserPassword: String = ""

    override fun toString(): String {
        return "LoginViewModel(isUserLoggedIn=$isUserLoggedIn, currUserEmail='$currUserEmail', currUserPassword='$currUserPassword')"
    }
}