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
import com.beleavemebe.androidlabentrytask.R
import com.beleavemebe.androidlabentrytask.utils.LoginArgsValidator

class LoginFragment : Fragment() {
    companion object {
        private const val TAG = "LoginFragment"

        fun newInstance() : LoginFragment {
            return LoginFragment()
        }
    }

    interface Callbacks {
        fun onRegister(email: String, password: String)
        fun onLogin()
    }

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button

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
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        Log.d(TAG, "onCreateView(...) called")

        findViewsById(rootView)
        initButtons()

        return rootView
    }

    private fun initButtons() {
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
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")

        val emailWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!LoginArgsValidator.isEmailValid(s.toString())) {
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
                if (!LoginArgsValidator.isPasswordValid(s.toString())) {
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
        if (!LoginArgsValidator.isEmailValid(email) || !LoginArgsValidator.isPasswordValid(password)) {
            updateButtons(enabled=false)
            Log.d(TAG, "Disabled buttons due to invalid email/password")
        } else {
            updateButtons(enabled=true)
            Log.d(TAG, "Enabled buttons, email & password are OK")
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

    private fun findViewsById(rootView: View) {
        etEmail = rootView.findViewById(R.id.email_et)
        etPassword = rootView.findViewById(R.id.password_et)
        registerButton = rootView.findViewById(R.id.register_button)
        loginButton = rootView.findViewById(R.id.login_button)
    }
}
