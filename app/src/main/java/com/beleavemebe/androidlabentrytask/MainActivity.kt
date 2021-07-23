package com.beleavemebe.androidlabentrytask

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.beleavemebe.androidlabentrytask.fragments.LoginFragment
import com.beleavemebe.androidlabentrytask.fragments.RegisterFragment
import com.beleavemebe.androidlabentrytask.fragments.UserFragment
import com.beleavemebe.androidlabentrytask.model.User

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
        if (currentFragment == null) {
            val fragment = LoginFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    private fun setLoginFragment() {
        val fragment = LoginFragment.newInstance()
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
            .addToBackStack(null)
            .commit()
    }

    private fun setUserFragment() {
        val fragment = UserFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onRegister(email: String, password: String) {
        setRegisterFragment(email, password)
    }

    override fun onLogin() {
        setUserFragment(
            // TODO user
        )
    }

    override fun onLogout() {
        setLoginFragment()
    }

    override fun onRegisterUser(email: String, password: String, name: String, surname: String) {
        val user = User(email, password, name, surname)
        // TODO add user to database
        setUserFragment(
            // TODO user
        )
    }

    override fun onCancelRegister() {
        setLoginFragment()
    }
}
