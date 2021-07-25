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
import com.beleavemebe.androidlabentrytask.MainActivity
import com.beleavemebe.androidlabentrytask.R
import com.beleavemebe.androidlabentrytask.UserViewModel
import com.beleavemebe.androidlabentrytask.model.User

class UserFragment : Fragment() {
    companion object {
        private const val TAG = "UserFragment"
        private const val ARG_EMAIL = "com.beleavemebe.androidlabentrytask.$TAG.email"
        private const val ARG_PASSWORD = "com.beleavemebe.androidlabentrytask.$TAG.password"

        fun newInstance(email: String, password: String): UserFragment {
            val args = Bundle().apply {
                putString(ARG_EMAIL, email)
                putString(ARG_PASSWORD, password)
            }
            return UserFragment().apply {
                arguments = args
            }
        }
    }

    interface Callbacks {
        fun onUserLogout()
        fun onUserNotFound(email: String, password: String) // fragment was set up, but no user was found in db
        fun onPasswordIncorrect() // fragment was set up, but ARG_PASSWORD didn't match with database password
        fun onLoginSuccess(email: String, password: String)
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
        assert(context is MainActivity)
        callbacks = context as Callbacks?
    }

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = User.USER_NONE
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
        setFragmentVisibility(false)
        logoutButton.setOnClickListener {
            callbacks?.onUserLogout()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email: String = arguments?.getString(ARG_EMAIL) as String
        val password: String = arguments?.getString(ARG_PASSWORD) as String
        userViewModel.loadUser(email)
        userViewModel.userLiveData.observe(
            viewLifecycleOwner,
            Observer { user ->
                when {
                    user == null -> {
                        callbacks?.onUserNotFound(email, password)
                    }
                    user.password != password -> {
                        callbacks?.onPasswordIncorrect()
                    }
                    else -> {
                        this.user = user
                        updateInfoTVs()
                        setFragmentVisibility(true)
                    }
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

    private lateinit var tvEmailHead : TextView
    private lateinit var tvNameHead : TextView
    private lateinit var tvSurnameHead : TextView

    private fun setFragmentVisibility(flag: Boolean) {
        for (view in listOf(
            avatar,
            tvEmail,
            tvName,
            tvSurname,
            logoutButton,
            tvEmailHead,
            tvNameHead,
            tvSurnameHead)
        ) {
            view.visibility = if (flag) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun findViewsById(rootView: View) {
        avatar = rootView.findViewById(R.id.avatar)
        tvEmail = rootView.findViewById(R.id.email_tv)
        tvName = rootView.findViewById(R.id.name_tv)
        tvSurname = rootView.findViewById(R.id.surname_tv)
        logoutButton = rootView.findViewById(R.id.logout_button)

        tvEmailHead = rootView.findViewById(R.id.email_head_tv)
        tvNameHead = rootView.findViewById(R.id.name_head_tv)
        tvSurnameHead = rootView.findViewById(R.id.surname_head_tv)
    }
}