package com.example.chat.fragments.authorization.sign_up

import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.chat.App
import com.example.chat.R
import com.example.chat.fragments.authorization.sign_in.SignInFragment
import com.example.chat.fragments.base_input.BaseInputFragment
import com.example.chat.fragments.chats_list.ChatsFragment
import com.example.domain.errors.sealed.PropertyPath
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : BaseInputFragment(), SignUpView {

    @InjectPresenter
    lateinit var presenter: SignUpPresenter

    @ProvidePresenter
    fun providePresenter(): SignUpPresenter = App.appComponent.provideSignUpPresenter()

    override val layoutId = R.layout.fragment_sign_up

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        removeEditTextErrors()
        spannableTextView()
    }

    override fun setUpListeners() {
        tvCancel.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        tvSignIn.setOnClickListener {
            openSignIn()
        }

        bSignUp.setOnClickListener {
            removeEditTextErrors()
            presenter.signUp(
                etEmail.text.toString(),
                etPassword.text.toString(),
                etConfirmPassword.text.toString()
            )
        }
    }

    override fun setErrorInput(propertyPath: String?, message: String?) {

        if (propertyPath.isNullOrBlank()) return

        when (PropertyPath.stringPropertyPathToPropertyPath(propertyPath)) {
            PropertyPath.Password -> {
                showErrorInput(tilPassword, etPassword, message)
            }
            PropertyPath.Email -> {
                showErrorInput(tilEmail, etEmail, message)
            }
            PropertyPath.ConfirmPassword -> {
                showErrorInput(tilConfirmPassword, etConfirmPassword, message)
            }
        }
    }

    override fun setStringFromResources(stringId: Int) {
        presenter.errorString = resources.getString(stringId)
    }

    override fun setSuccessfulMessage() {
        Toast.makeText(
            context,
            resources.getString(R.string.successful_sign_up),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun openSignIn() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, SignInFragment())
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun openChatsFragment() {
        activity?.supportFragmentManager?.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(
                R.id.container,
                ChatsFragment()
            )
            ?.commit()
    }

    private fun removeEditTextErrors() {
        removeEditTextErrors(tilEmail, etEmail, R.drawable.ic_email)
        removeEditTextErrors(tilPassword, etPassword, R.drawable.ic_password)
        removeEditTextErrors(tilConfirmPassword, etConfirmPassword, R.drawable.ic_password)
    }

    private fun spannableTextView() {
        spannableHint(R.string.email, etEmail)
        spannableHint(R.string.old_password, etPassword)
        spannableHint(R.string.confirm_password, etConfirmPassword)
    }

    private fun spannableHint(stringId: Int, editText: EditText) {
        val mainString = resources.getString(stringId)
        val builder = SpannableStringBuilder()
        val greyColoredString = SpannableString(mainString)
        greyColoredString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorGrey)),
            0,
            mainString.length,
            0
        )
        builder.append(greyColoredString)

        val importantString = resources.getString(R.string.input_important)
        val redColoredString = SpannableString(importantString)
        redColoredString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorRed)),
            0,
            importantString.length,
            0
        )
        builder.append(redColoredString)

        editText.hint = builder
    }
}