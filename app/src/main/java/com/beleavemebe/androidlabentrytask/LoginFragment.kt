package com.beleavemebe.androidlabentrytask

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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

        fun newInstance() : LoginFragment {
            return LoginFragment()
        }
    }

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button

    interface Callbacks {
        fun onRegister(email: String, password: String)
        fun onLogin()
    }

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        Log.d(TAG, "onCreateView(...) called")

        etEmail = view.findViewById(R.id.email_et)
        etPassword = view.findViewById(R.id.password_et)
        registerButton = view.findViewById(R.id.register_button)
        loginButton = view.findViewById(R.id.login_button)

        refreshButtons()
        registerButton.setOnClickListener {
            callbacks?.onRegister(
                etEmail.text.toString(),
                etPassword.text.toString()
            )
        }
        loginButton.setOnClickListener {
            callbacks?.onLogin()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")

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

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView() called")
        etEmail.setText("")
        etPassword.setText("")
    }

    private fun refreshButtons() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        if (!isEmailValid(email) || !isPasswordValid(password)) {
            updateButtons(enabled=false)
            Log.d(TAG, "Refreshed buttons: locked")
        } else {
            updateButtons(enabled=true)
            Log.d(TAG, "Refreshed buttons: unlocked")
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

    private fun isEmailValid(email: String?) : Boolean {
        val result = Patterns.EMAIL_ADDRESS.matcher(email ?: "").matches()
        Log.d(TAG, "Email $email is valid = $result")
        return result
    }

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

        val result = uppercaseLetterIsPresent && lowercaseLetterIsPresent && digitIsPresent
        Log.d(TAG, "Password $password is valid = $result")
        return result
    }
}
