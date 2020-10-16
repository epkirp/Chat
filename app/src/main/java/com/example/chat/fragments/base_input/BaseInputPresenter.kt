package com.example.chat.fragments.base_input

import com.example.chat.R
import com.example.chat.fragments.base.BasePresenter
import com.example.chat.utils.*
import com.example.domain.errors.ErrorEntity
import com.example.domain.errors.sealed.PropertyPath
import java.util.*

abstract class BaseInputPresenter<T : BaseInputView> : BasePresenter<T>() {

    var errorString = ""

    private fun createError(propertyPath: PropertyPath, stringId: Int): ErrorEntity {
        viewState.setStringFromResources(stringId)
        return ErrorEntity(
            PropertyPath.propertyPathToStringPropertyPath(propertyPath),
            errorString
        )
    }

    fun getErrorsPassword(password: String): Boolean {

        val errorMessage = ArrayList<ErrorEntity>()

        if (!areAllFieldsFilled(password)) {
            errorMessage.add(createError(PropertyPath.Password, R.string.error_empty_password))
        } else if (!isValidPassword(password)) {
            errorMessage.add(createError(PropertyPath.Password, R.string.error_password_min_length))
        }

        if (errorMessage.isNotEmpty()) {
            setErrors(errorMessage)
            return true
        }
        return false
    }

    fun getErrorsConfirmPassword(password: String, confirmPassword: String): Boolean {

        val errorMessage = ArrayList<ErrorEntity>()

        if (!areAllFieldsFilled(password)) {
            errorMessage.add(createError(PropertyPath.Password, R.string.error_empty_password))
        } else if (!isValidPassword(password)) {
            errorMessage.add(createError(PropertyPath.Password, R.string.error_password_min_length))
        } else if (!isValidConfirmPassword(password, confirmPassword)) {
            errorMessage.add(createError(PropertyPath.Password, R.string.error_confirm_password))
            errorMessage.add(
                createError(
                    PropertyPath.ConfirmPassword,
                    R.string.error_confirm_password
                )
            )
        }

        if (errorMessage.isNotEmpty()) {
            setErrors(errorMessage)
            return true
        }
        return false
    }

    fun getErrorsUserName(userName: String): Boolean {

        val errorMessage = ArrayList<ErrorEntity>()

        if (!areAllFieldsFilled(userName)) {
            errorMessage.add(createError(PropertyPath.UserName, R.string.error_empty_user_name))
        }

        if (errorMessage.isNotEmpty()) {
            setErrors(errorMessage)
            return true
        }
        return false
    }

    fun getErrorsEmail(email: String): Boolean {

        val errorMessage = ArrayList<ErrorEntity>()

        if (!areAllFieldsFilled(email)) {
            errorMessage.add(createError(PropertyPath.Email, R.string.error_empty_email))
        } else if (!isValidEmail(email)) {
            errorMessage.add(createError(PropertyPath.Email, R.string.error_bad_email))
        }

        if (errorMessage.isNotEmpty()) {
            setErrors(errorMessage)
            return true
        }
        return false
    }

    fun getErrorsBirthday(birthday: Date?): Boolean {

        val errorMessage = ArrayList<ErrorEntity>()

        if (!isValidBirthday(birthday)) {
            errorMessage.add(createError(PropertyPath.Birthday, R.string.error_birthday))
        }

        if (errorMessage.isNotEmpty()) {
            setErrors(errorMessage)
            return true
        }
        return false
    }

    fun getErrorsChangePassword(
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ): Boolean {

        val errorMessage = ArrayList<ErrorEntity>()

        getErrorsPassword(oldPassword)

        if (!areAllFieldsFilled(newPassword)) {
            errorMessage.add(createError(PropertyPath.NewPassword, R.string.error_empty_password))
        } else if (!isValidPassword(newPassword)) {
            errorMessage.add(
                createError(
                    PropertyPath.NewPassword,
                    R.string.error_password_min_length
                )
            )
        } else if (!isValidConfirmPassword(newPassword, confirmPassword)) {
            errorMessage.add(createError(PropertyPath.NewPassword, R.string.error_confirm_password))
            errorMessage.add(
                createError(
                    PropertyPath.ConfirmPassword,
                    R.string.error_confirm_password
                )
            )
        }

        if (errorMessage.isNotEmpty()) {
            setErrors(errorMessage)
            return true
        }
        return false
    }

    fun getFirebaseErrors(errorCode: String?, errorMessage: String?): Boolean {

        if (errorMessage.isNullOrBlank()) {
            viewState.serverErrorMessage()
            return true
        } else if (errorCode.isNullOrBlank()) {
            viewState.showMessage(errorMessage)
            return true
        }

        val pair = errorCode.findAnyOf(PropertyPath.getAllPropertyPaths(), 0, true)

        if (pair != null) {
            setErrors(arrayListOf(ErrorEntity(pair.second, errorMessage)))
            return true
        }

        return false
    }

    private fun setErrors(errorMessage: List<ErrorEntity>) {
        errorMessage.forEach {
            viewState.setErrorInput(it.propertyPath, it.message)
        }
    }
}
