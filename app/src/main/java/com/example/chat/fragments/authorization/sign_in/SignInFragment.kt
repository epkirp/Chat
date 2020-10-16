package com.example.chat.fragments.authorization.sign_in

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.chat.App
import com.example.chat.R
import com.example.chat.fragments.base_input.BaseInputFragment
import com.example.chat.fragments.chats_list.ChatsFragment
import com.example.domain.errors.sealed.PropertyPath
import com.example.chat.fragments.authorization.sign_up.SignUpFragment
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : BaseInputFragment(), SignInView {

    @InjectPresenter
    lateinit var presenter: SignInPresenter

    @ProvidePresenter
    fun providePresenter(): SignInPresenter = App.appComponent.provideSignInPresenter()

    override val layoutId = R.layout.fragment_sign_in

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        removeEditTextErrors()
    }

    override fun setUpListeners() {
        bSignIn.setOnClickListener {
            removeEditTextErrors()
            presenter.login(etEmail.text.toString(), etPassword.text.toString())
        }

        tvSignUp.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, SignUpFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    override fun openChats() {
        activity?.supportFragmentManager?.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, ChatsFragment())
            ?.commit()
    }

    override fun setErrorInput(propertyPath: String?, message: String?) {

        propertyPath?.let {
            when (PropertyPath.stringPropertyPathToPropertyPath(propertyPath)) {
                PropertyPath.Password -> {
                    showErrorInput(tilPassword, etPassword, message)
                }

                PropertyPath.Email -> {
                    showErrorInput(tilEmail, etEmail, message)
                }
            }
        }
    }

    override fun setStringFromResources(stringId: Int) {
        presenter.errorString = resources.getString(stringId)
    }

    private fun removeEditTextErrors() {
        removeEditTextErrors(tilEmail, etEmail, R.drawable.ic_email)
        removeEditTextErrors(tilPassword, etPassword, R.drawable.ic_password)
    }
}