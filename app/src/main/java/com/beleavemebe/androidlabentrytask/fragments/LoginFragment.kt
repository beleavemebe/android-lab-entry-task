package com.beleavemebe.androidlabentrytask.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.beleavemebe.androidlabentrytask.MainActivity
import com.beleavemebe.androidlabentrytask.R
import com.beleavemebe.androidlabentrytask.utils.LoginArgsValidator
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {
    companion object {
        private const val TAG = "LoginFragment"

        fun newInstance() : LoginFragment {
            return LoginFragment()
        }
    }

    interface Callbacks {
        fun onRegisterButton(email: String, password: String)
        fun onLoginButton(view: View, email: String, password: String)
    }

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var tiEmail: TextInputLayout
    private lateinit var tiPassword: TextInputLayout
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        assert(context is MainActivity)
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
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        Log.d(TAG, "onCreateView(...) called")

        findViewsById(rootView)
        initButtons()

        return rootView
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")

        addTextWatchers()
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

    private fun initButtons() {
        refreshButtonsAndDisplayErrors()
        btnRegister.setOnClickListener {
            callbacks?.onRegisterButton(
                etEmail.text.toString(),
                etPassword.text.toString()
            )
        }
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            callbacks?.onLoginButton(
                btnLogin,
                email,
                password
            )
        }
    }

    private fun refreshButtonsAndDisplayErrors() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        if (!LoginArgsValidator.isEmailValid(email)) {
            tiPassword.error = null
            if (email != "") {
                tiEmail.error = getString(R.string.invalid_email)
            }

            Log.d(TAG, "Disabled buttons due to invalid email")
            updateButtons(enabled=false)
        } else if (!LoginArgsValidator.isPasswordValid(password)) {
            tiEmail.error = null
            if (password != "") {
                tiPassword.error = getString(R.string.invalid_password)
            }
            Log.d(TAG, "Disabled buttons due to invalid password")
            updateButtons(enabled=false)
        } else {
            tiEmail.error = null
            tiPassword.error = null
            updateButtons(enabled=true)
            Log.d(TAG, "Enabled buttons, email & password are OK")
        }
    }

    private fun updateButtons(enabled: Boolean) {
        btnRegister.apply {
            isEnabled = enabled
        }
        btnLogin.apply {
            isEnabled = enabled
        }
    }

    private fun addTextWatchers() {
        val emailWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) = refreshButtonsAndDisplayErrors()
        }
        val passwordWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) = refreshButtonsAndDisplayErrors()
        }

        etEmail.addTextChangedListener(emailWatcher)
        etPassword.addTextChangedListener(passwordWatcher)
    }

    private fun findViewsById(rootView: View) {
        etEmail = rootView.findViewById(R.id.email_et)
        etPassword = rootView.findViewById(R.id.password_et)
        btnRegister = rootView.findViewById(R.id.register_btn)
        btnLogin = rootView.findViewById(R.id.login_btn)
        tiEmail = rootView.findViewById(R.id.email_ti)
        tiPassword = rootView.findViewById(R.id.password_ti)
    }
}
