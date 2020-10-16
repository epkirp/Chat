package com.example.chat.fragments

import com.example.chat.R
import com.example.chat.fragments.authorization.sign_in.SignInFragment
import com.example.chat.fragments.authorization.sign_up.SignUpFragment
import com.example.chat.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_welcome

    override fun setUpListeners() {

        super.setUpListeners()

        bCreateAccount.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.container,
                    SignUpFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }

        bHaveAccount.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.container,
                    SignInFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}