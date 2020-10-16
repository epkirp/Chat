package com.example.chat.main_activity

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MainView: MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openWelcomeFragment()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openChatsFragment()
}