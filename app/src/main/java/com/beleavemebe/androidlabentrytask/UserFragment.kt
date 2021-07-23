package com.beleavemebe.androidlabentrytask

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class UserFragment : Fragment() {
    companion object {
        fun newInstance() : UserFragment {
            return UserFragment()
        }
    }

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    interface Callbacks {
        fun onLogout()
    }

    private var callbacks: Callbacks? = null
    private lateinit var logoutButton: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        logoutButton = view.findViewById(R.id.logout_button)
        logoutButton.setOnClickListener {
            callbacks?.onLogout()
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}