package com.beleavemebe.androidlabentrytask

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class LoginFragment : Fragment() {
    companion object {
        private const val TAG = "LoginFragment"
        private const val MIN_PASSWORD_LENGTH = 6

        fun newFragment() : LoginFragment {
            return LoginFragment()
        }
    }

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        etEmail = view.findViewById(R.id.email_et)
        etPassword = view.findViewById(R.id.password_et)
        registerButton = view.findViewById(R.id.register_button)
        loginButton = view.findViewById(R.id.login_button)

        updateButtons(enabled=false)
        registerButton.setOnClickListener {

        }
        loginButton.setOnClickListener {
            val userFragment = UserFragment.newFragment()
            setFragment(userFragment)
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        val emailWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isEmailValid(s.toString())) {
                    // TODO do
                }
            }

            override fun afterTextChanged(s: Editable?) {
                refreshButtons()
            }
        }

        val passwordWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isPasswordValid(s.toString())) {
                    // TODO do
                }
            }

            override fun afterTextChanged(s: Editable?) {
                refreshButtons()
            }
        }

        etEmail.addTextChangedListener(emailWatcher)
        etPassword.addTextChangedListener(passwordWatcher)
    }

    private fun setFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun refreshButtons() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        if (!isEmailValid(email) || !isPasswordValid(password)) {
            updateButtons(enabled=false)
        } else {
            updateButtons(enabled=true)
        }
    }

    private fun updateButtons(enabled: Boolean) {
        registerButton.apply {
            isEnabled = enabled
        }
        loginButton.apply {
            isEnabled = enabled
        }
    }

    private fun isEmailValid(email: String?) : Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email ?: "").matches()

    private fun isPasswordValid(password: String?) : Boolean {
        if (password == null || password.length < MIN_PASSWORD_LENGTH) return false

        var uppercaseLetterIsPresent = false
        var lowercaseLetterIsPresent = false
        var digitIsPresent = false

        password.forEach {
            val char: Char = Character.valueOf(it)
            when {
                char.isUpperCase() -> uppercaseLetterIsPresent = true
                char.isLowerCase() -> lowercaseLetterIsPresent = true
                char.isDigit() -> digitIsPresent = true
            }
        }

        return uppercaseLetterIsPresent && lowercaseLetterIsPresent && digitIsPresent
    }
}