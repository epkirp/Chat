package com.example.chat.fragments.authorization.sign_in

import com.arellomobile.mvp.InjectViewState
import com.example.chat.fragments.base_input.BaseInputPresenter
import com.example.domain.gateways.AuthorizationGateway
import com.google.firebase.auth.FirebaseAuthException
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class SignInPresenter @Inject constructor(
    private val authorizationGateway: AuthorizationGateway
) : BaseInputPresenter<SignInView>() {

    fun login(email: String, password: String) {

        if (areThereErrors(email, password)) {
            return
        }

        authorizationGateway.singIn(email, password)
            .subscribeOn(Schedulers.io())
            .subscribe({
                viewState.openChats()
            }, {
                if (it is FirebaseAuthException) {
                    getFirebaseErrors(it.errorCode, it.message)
                } else {
                    viewState.serverErrorMessage()
                }
            }).let(compositeDisposable::add)
    }

    private fun areThereErrors(email: String, password: String): Boolean {
        return (getErrorsPassword(password) || getErrorsEmail(email))
    }
}