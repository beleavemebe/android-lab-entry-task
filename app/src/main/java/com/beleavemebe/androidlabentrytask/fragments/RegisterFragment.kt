package com.beleavemebe.androidlabentrytask.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.beleavemebe.androidlabentrytask.R
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : Fragment() {
    companion object {
        private const val ARG_EMAIL = "com.beleavemebe.androidlabentrytask.email"
        private const val ARG_PASSWORD = "com.beleavemebe.androidlabentrytask.password"

        fun newInstance(email: String, password: String) : RegisterFragment {
            val args = Bundle().apply {
                putString(ARG_EMAIL, email)
                putString(ARG_PASSWORD, password)
            }
            return RegisterFragment().apply {
                arguments = args
            }
        }
    }

    interface Callbacks {
        fun onRegisterUser(email: String,
                           password: String,
                           name: String,
                           surname: String)
        fun onCancelRegister()
    }

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etName: EditText
    private lateinit var etSurname: EditText
    private lateinit var tiName: TextInputLayout
    private lateinit var tiSurname: TextInputLayout
    private lateinit var btnExit: Button
    private lateinit var btnRegister: Button

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_register, container, false)

        findViewsById(rootView)
        initEmailAndPasswordTVs()
        initButtons()

        return rootView
    }

    override fun onStart() {
        super.onStart()
        addTextWatchers()
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun initEmailAndPasswordTVs() {
        etEmail.setText(
            arguments?.getString(ARG_EMAIL) ?: ""
        )
        etPassword.setText(
            arguments?.getString(ARG_PASSWORD) ?: ""
        )
    }

    private fun initButtons() {
        btnExit.setOnClickListener {
            callbacks?.onCancelRegister()
        }
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val name = etName.text.toString()
            val surname = etSurname.text.toString()
            when {
                name == "" -> {
                    tiName.error = getString(R.string.empty_field)
                    tiSurname.error = null
                }
                surname == "" -> {
                    tiName.error = null
                    tiSurname.error = getString(R.string.empty_field)
                }
                else -> {
                    tiName.error = null
                    tiSurname.error = null
                    callbacks?.onRegisterUser(
                        email,
                        password,
                        name,
                        surname
                    )
                }
            }
        }
    }

    private fun addTextWatchers() {
        val nameWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) = tiName.setError(null)
        }
        val surnameWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) = tiSurname.setError(null)
        }

        etName.addTextChangedListener(nameWatcher)
        etSurname.addTextChangedListener(surnameWatcher)
    }

    private fun findViewsById(rootView: View) {
        etEmail = rootView.findViewById(R.id.email_et)
        etPassword = rootView.findViewById(R.id.password_et)
        etName = rootView.findViewById(R.id.name_et)
        etSurname = rootView.findViewById(R.id.surname_et)
        tiName = rootView.findViewById(R.id.name_ti)
        tiSurname = rootView.findViewById(R.id.surname_ti)
        btnExit = rootView.findViewById(R.id.exit_button)
        btnRegister = rootView.findViewById(R.id.register_btn)
    }
}