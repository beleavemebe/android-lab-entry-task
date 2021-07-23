package com.beleavemebe.androidlabentrytask.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.beleavemebe.androidlabentrytask.R

class UserFragment : Fragment() {
    companion object {
        fun newInstance() : UserFragment {
            return UserFragment()
        }
    }

    interface Callbacks {
        fun onLogout()
    }

    private lateinit var logoutButton: Button
    private lateinit var avatar: ImageView
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
        val rootView = inflater.inflate(R.layout.fragment_user, container, false)

        findViewsById(rootView)
        logoutButton.setOnClickListener {
            callbacks?.onLogout()
        }

        return rootView
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun findViewsById(rootView: View) {
        logoutButton = rootView.findViewById(R.id.logout_button)
        avatar = rootView.findViewById(R.id.avatar)
    }
}