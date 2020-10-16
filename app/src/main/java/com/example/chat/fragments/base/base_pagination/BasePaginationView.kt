package com.example.chat.fragments.base.base_pagination

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.chat.fragments.base.BaseView

interface BasePaginationView<I : Any> : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showNewItems(newItemsCount: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showInitialItems(items: ArrayList<I>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changePlaceholderVisibilityState(isVisible: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeLoaderVisibilityState(isVisible: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeRefreshVisibilityState(isVisible: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun changePaginationLoaderState(isVisible: Boolean)
}
