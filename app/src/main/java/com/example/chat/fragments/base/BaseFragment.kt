package com.example.chat.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.example.chat.R

abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpListeners()
    }

    open fun setUpListeners() {}

    open fun setUpUI() {}

    override fun serverErrorMessage() {
        Toast.makeText(context, resources.getString(R.string.server_error), Toast.LENGTH_SHORT)
            .show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
            .show()
    }

    override fun showMessage(messageId: Int) {
        Toast.makeText(context, resources.getString(messageId), Toast.LENGTH_SHORT)
            .show()
    }
}