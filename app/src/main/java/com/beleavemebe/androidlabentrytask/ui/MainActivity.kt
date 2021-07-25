package com.beleavemebe.androidlabentrytask.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.beleavemebe.androidlabentrytask.R
import com.beleavemebe.androidlabentrytask.model.User
import com.beleavemebe.androidlabentrytask.repositories.UserRepository
import com.beleavemebe.androidlabentrytask.ui.login.LoginFragment
import com.beleavemebe.androidlabentrytask.ui.login.LoginViewModel
import com.beleavemebe.androidlabentrytask.ui.register.RegisterFragment
import com.beleavemebe.androidlabentrytask.ui.user.UserFragment
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(),
        LoginFragment.Callbacks,
        UserFragment.Callbacks,
        RegisterFragment.Callbacks
{
    companion object {
        private const val TAG = "MainActivity"
        private const val ARG_USER_LOGGED_IN = "com.beleavemebe.androidlabentrytask.$TAG.user_logged_in"
        private const val ARG_EMAIL = "com.beleavemebe.androidlabentrytask.$TAG.email"
        private const val ARG_PASSWORD = "com.beleavemebe.androidlabentrytask.$TAG.password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Disabling night mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        // Stay logged in
        if (savedInstanceState?.getBoolean(ARG_USER_LOGGED_IN, false) == true) {
            val email = savedInstanceState.getString(ARG_EMAIL, "")
            val password = savedInstanceState.getString(ARG_PASSWORD, "")
            loginViewModel.isUserLoggedIn = true
            loginViewModel.currUserEmail = email
            loginViewModel.currUserPassword = password
            setLoginFragment(email, password)
        } else if (currentFragment == null) {
            // Initial fragment
            setLoginFragment()
        }
    }

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private fun setLoginFragment(email: String = "", password: String = "") {
        val fragment = LoginFragment.newInstance(email, password)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun setRegisterFragment(email: String, password: String) {
        val fragment = RegisterFragment.newInstance(email, password)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun setUserFragment(email: String, password: String) {
        val fragment = UserFragment.newInstance(email, password)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onRegisterButton(email: String, password: String) {
        setRegisterFragment(email, password)
    }

    override fun onLoginButton(email: String, password: String) {
        setUserFragment(email, password)
    }

    override fun onUserLogout() {
        loginViewModel.apply {
            isUserLoggedIn = false
            currUserEmail = ""
            currUserPassword = ""
        }
        Log.d(TAG, loginViewModel.toString())
        setLoginFragment()
    }

    override fun onRegisterUser(email: String, password: String, name: String, surname: String) {
        val user = User(email, password, name, surname)
        UserRepository.getInstance().addUser(user)
        setLoginFragment()
        showMessage(
            getString(R.string.registration_success, email)
        )
    }

    override fun onCancelRegister(email: String, password: String) {
        setLoginFragment(email, password)
    }

    override fun onUserNotFound(email: String, password: String) {
        setLoginFragment(email, password)
        showMessage(
            getString(R.string.user_not_found, email)
        )
    }

    override fun onPasswordIncorrect() {
        setLoginFragment()
        showMessage(
            getString(R.string.incorrect_password)
        )
    }

    override fun onLoginSuccess(email: String, password: String) {
        loginViewModel.apply {
            isUserLoggedIn = true
            currUserEmail = email
            currUserPassword = password
        }
        Log.d(TAG, loginViewModel.toString())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putBoolean(ARG_USER_LOGGED_IN, loginViewModel.isUserLoggedIn)
            putString(ARG_EMAIL, loginViewModel.currUserEmail)
            putString(ARG_PASSWORD, loginViewModel.currUserPassword)
        }
    }

    private fun showMessage(message: String) {
        findViewById<View>(R.id.fragment_container).also {
            Snackbar.make(
                it,
                message,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}
