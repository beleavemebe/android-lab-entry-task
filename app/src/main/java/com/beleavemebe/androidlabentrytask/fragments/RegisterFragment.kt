package com.beleavemebe.androidlabentrytask.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.beleavemebe.androidlabentrytask.R

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
            callbacks?.onRegisterUser(
                etEmail.text.toString(),
                etPassword.text.toString(),
                etName.text.toString(),
                etSurname.text.toString()
            )
        }
    }

    private fun findViewsById(rootView: View) {
        etEmail = rootView.findViewById(R.id.email_et)
        etPassword = rootView.findViewById(R.id.password_et)
        etName = rootView.findViewById(R.id.name_et)
        etSurname = rootView.findViewById(R.id.surname_et)
        btnExit = rootView.findViewById(R.id.exit_button)
        btnRegister = rootView.findViewById(R.id.register_btn)
    }
}