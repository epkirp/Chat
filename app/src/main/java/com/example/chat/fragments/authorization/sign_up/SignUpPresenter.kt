package com.example.chat.fragments.authorization.sign_up

import com.arellomobile.mvp.InjectViewState
import com.example.chat.fragments.base_input.BaseInputPresenter
import com.example.domain.gateways.AuthorizationGateway
import com.google.firebase.auth.FirebaseAuthException
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class SignUpPresenter @Inject constructor(
    private val authorizationGateway: AuthorizationGateway
) : BaseInputPresenter<SignUpView>() {

    fun signUp(email: String, password: String, confirmPassword: String) {
        if (areThereErrors(email, password, confirmPassword)) {
            return
        }

        authorizationGateway.signUp(email, password)
            .subscribeOn(Schedulers.io())
            .subscribe({
                viewState.setSuccessfulMessage()
                viewState.openChatsFragment()
            }, {
                if (it is FirebaseAuthException) {
                    getFirebaseErrors(it.errorCode, it.message)
                } else {
                    viewState.serverErrorMessage()
                }
            }).let(compositeDisposable::add)
    }

    private fun areThereErrors(email: String, password: String, confirmPassword: String): Boolean {
        return getErrorsConfirmPassword(password, confirmPassword) || getErrorsEmail(email)
    }
}