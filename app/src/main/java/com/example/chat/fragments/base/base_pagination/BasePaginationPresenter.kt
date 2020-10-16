package com.example.chat.fragments.base.base_pagination

import com.example.chat.constants.PAGE_LIMIT
import com.example.chat.fragments.base.BasePresenter
import com.google.firebase.firestore.DocumentSnapshot
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BasePaginationPresenter<T : BasePaginationView<I>, I : Any> : BasePresenter<T>() {

    private var totalItems = 1
    private var countOfPages = 1
    private var isLoading = false
    private val items = ArrayList<I>()

    protected var currentPage = 1

    abstract fun getItems(page: Int = 1, limit: Int = PAGE_LIMIT): Single<List<I>?>
    abstract fun initValues(limit: Int = PAGE_LIMIT): Single<Int?>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadFirstPage()
    }

    open fun onSwipeToRefresh() {
        viewState.changePlaceholderVisibilityState(false)
        viewState.changeRefreshVisibilityState(true)
        loadFirstPage()
        viewState.changeRefreshVisibilityState(false)
    }

    open fun onNewPageScrolled() {
        if (isLoading || currentPage - 1 >= countOfPages) {
            return
        }
        loadNewPage()
    }


    protected open fun loadFirstPage() {
        resetData()

        viewState.changePlaceholderVisibilityState(false)
        initValues(PAGE_LIMIT).flatMap {
            totalItems = it
            countOfPages = totalItems / PAGE_LIMIT
            countOfPages += if (totalItems % PAGE_LIMIT != 0) {
                1
            } else {
                0
            }

            getItems(currentPage, PAGE_LIMIT)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.changeLoaderVisibilityState(true)
                isLoading = true
            }
            .doOnSuccess { response ->
                viewState.changePlaceholderVisibilityState(response.isNullOrEmpty())
            }
            .doFinally {
                viewState.changeLoaderVisibilityState(false)
                isLoading = false
            }
            .subscribe({ response ->
                if (response != null) {
                    items.apply {
                        clear()
                        addAll(response)
                    }
                    currentPage++
                    viewState.showInitialItems(items)
                } else {
                    onFailureLoad()
                }
            }, {
                onFailureLoad()
            })
            .let(compositeDisposable::add)
    }

    open fun loadNewPage() {
        getItems(currentPage, PAGE_LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading = true
                viewState.changePaginationLoaderState(isLoading)
            }
            .doFinally {
                isLoading = false
                viewState.changePaginationLoaderState(isLoading)
            }
            .subscribe({ response ->
                if (response != null) {
                    items.apply {
                        addAll(response)
                    }
                    currentPage++
                    viewState.showNewItems(response.size)
                } else {
                    onFailureLoad()
                }
            }, {
                onFailureLoad()
            })
            .let(compositeDisposable::add)
    }

    private fun resetData() {
        isLoading = false
        currentPage = 1
        items.clear()
    }

    private fun onFailureLoad() {
        resetData()

        viewState.changePlaceholderVisibilityState(true)
        viewState.changeRefreshVisibilityState(false)
        viewState.changeLoaderVisibilityState(false)
    }
}