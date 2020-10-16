package com.example.chat.fragments.authorization.sign_up

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.chat.fragments.base_input.BaseInputView

interface SignUpView : BaseInputView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setSuccessfulMessage()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openSignIn()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openChatsFragment()
}