package com.example.chat.main_activity

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val user: FirebaseUser?
) : MvpPresenter<MainView>() {

    init {
        isUserAlreadySignIn()
    }

    private fun isUserAlreadySignIn() {
        if (user != null) {
            viewState.openChatsFragment()
        } else {
            viewState.openWelcomeFragment()
        }

    }
}