package com.example.chat.fragments.authorization.sign_in

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.chat.fragments.base_input.BaseInputView

interface SignInView : BaseInputView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openChats()
}