package com.example.chat.fragments.base_input

import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.chat.R
import com.example.chat.fragments.base.BaseFragment
import com.google.android.material.textfield.TextInputLayout

abstract class BaseInputFragment : BaseFragment(), BaseInputView {

    fun showErrorInput(
        textInputLayout: TextInputLayout,
        editText: EditText,
        message: String?
    ) {
        val errorInputIcon =
            ContextCompat.getDrawable(textInputLayout.context, R.drawable.ic_error_input) ?: return
        errorInputIcon.setBounds(0, 0, 60, 60)

        textInputLayout.error = message
        editText.background =
            ContextCompat.getDrawable(textInputLayout.context, R.drawable.error_edit_text_border)
        editText.setCompoundDrawables(null, null, errorInputIcon, null)
    }

    fun removeEditTextErrors(
        textInputLayout: TextInputLayout,
        editText: EditText,
        iconId: Int
    ) {
        textInputLayout.error = null
        editText.background =
            ContextCompat.getDrawable(textInputLayout.context, R.drawable.edit_text_border)
        val icon = ContextCompat.getDrawable(textInputLayout.context, iconId) ?: return
        icon.setBounds(0, 0, 60, 50)
        editText.setCompoundDrawables(null, null, icon, null)
    }

    override fun showMessageSuccessful() {
        Toast.makeText(
            context,
            resources.getString(R.string.successful_message),
            Toast.LENGTH_SHORT
        ).show()
    }
}