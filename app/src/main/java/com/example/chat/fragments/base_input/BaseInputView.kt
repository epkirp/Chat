package com.example.chat.fragments.base_input

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.chat.fragments.base.BaseView

interface BaseInputView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setErrorInput(propertyPath: String?, message: String?)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showMessageSuccessful()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setStringFromResources(stringId: Int)
}