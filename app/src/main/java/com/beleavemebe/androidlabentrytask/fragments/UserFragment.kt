package com.beleavemebe.androidlabentrytask.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.beleavemebe.androidlabentrytask.R
import com.beleavemebe.androidlabentrytask.model.User

class UserFragment : Fragment() {
    companion object {
        private const val ARG_USER = "com.beleavemebe.androidlabentrytask.user"

        fun newInstance(user: User) : UserFragment {
            val args = Bundle().apply {
                putSerializable(ARG_USER, user)
            }
            return UserFragment().apply {
                arguments = args
            }
        }
    }

    interface Callbacks {
        fun onLogoutButton()
    }

    private lateinit var avatar: ImageView
    private lateinit var tvEmail: TextView
    private lateinit var tvName: TextView
    private lateinit var tvSurname: TextView
    private lateinit var logoutButton: Button

    private var callbacks: Callbacks? = null

    private lateinit var user: User

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
        user = arguments?.getSerializable(ARG_USER) as User

        logoutButton.setOnClickListener {
            callbacks?.onLogoutButton()
        }

        updateInfoTVs()

        return rootView
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateInfoTVs() {
        tvEmail.text = user.email
        tvName.text = user.name
        tvSurname.text = user.surname
    }

    private fun findViewsById(rootView: View) {
        avatar = rootView.findViewById(R.id.avatar)
        tvEmail = rootView.findViewById(R.id.email_tv)
        tvName = rootView.findViewById(R.id.name_tv)
        tvSurname = rootView.findViewById(R.id.surname_tv)
        logoutButton = rootView.findViewById(R.id.logout_button)
    }
}