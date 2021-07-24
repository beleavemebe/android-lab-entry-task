package com.beleavemebe.androidlabentrytask

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.beleavemebe.androidlabentrytask.fragments.LoginFragment
import com.beleavemebe.androidlabentrytask.fragments.RegisterFragment
import com.beleavemebe.androidlabentrytask.fragments.UserFragment
import com.beleavemebe.androidlabentrytask.model.User
import com.beleavemebe.androidlabentrytask.repositories.UserRepository
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(),
        LoginFragment.Callbacks,
        UserFragment.Callbacks,
        RegisterFragment.Callbacks
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Disabling night mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        // Initial fragment
        if (currentFragment == null) setLoginFragment()
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

    private fun setUserFragment(email: String, password: String="") {
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
        setUserFragment(email)
    }

    override fun onLogoutButton() {
        setLoginFragment()
    }

    override fun onRegisterUser(email: String, password: String, name: String, surname: String) {
        val user = User(email, password, name, surname)
        UserRepository.getInstance().addUser(user)
        setLoginFragment()
    }

    override fun onCancelRegister(email: String, password: String) {
        setLoginFragment(email, password)
    }

    override fun onUserNotFound(email: String, password: String) {
        setLoginFragment(email, password)
        findViewById<View>(R.id.fragment_container).also {
            Snackbar.make(
                it,
                getString(R.string.user_not_found, email),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onPasswordIncorrect() {
        setLoginFragment()
        findViewById<View>(R.id.fragment_container).also {
            Snackbar.make(
                it,
                getString(R.string.incorrect_password),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}
