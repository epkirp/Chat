package com.example.chat.fragments.base

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface BaseView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun serverErrorMessage()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showMessage(message: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showMessage(messageId: Int)
}