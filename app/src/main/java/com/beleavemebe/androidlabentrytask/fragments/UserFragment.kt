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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.beleavemebe.androidlabentrytask.R
import com.beleavemebe.androidlabentrytask.UserViewModel
import com.beleavemebe.androidlabentrytask.model.User

class UserFragment : Fragment() {
    companion object {
        private const val ARG_EMAIL = "com.beleavemebe.androidlabentrytask.user"

        fun newInstance(email: String) : UserFragment {
            val args = Bundle().apply {
                putString(ARG_EMAIL, email)
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
    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = User("joe@example.com", "qwerty", "Joe", "McGill")
        val email: String = arguments?.getString(ARG_EMAIL) as String
        userViewModel.loadUser(email)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_user, container, false)

        findViewsById(rootView)
        logoutButton.setOnClickListener {
            callbacks?.onLogoutButton()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email: String = arguments?.getString(ARG_EMAIL) as String
        userViewModel.loadUser(email)
        userViewModel.userLiveData.observe(
            viewLifecycleOwner,
            Observer { user ->
                user?.let {
                    this.user = user
                    updateInfoTVs()
                }
            }
        )
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