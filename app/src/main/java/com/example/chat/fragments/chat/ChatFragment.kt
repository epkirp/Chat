package com.example.chat.fragments.chat

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.chat.App
import com.example.chat.R
import com.example.chat.adapters.ChatAdapter
import com.example.chat.fragments.base.BaseFragment
import com.example.chat.utils.changeVisibilityState
import com.example.domain.messages.Message
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.layout_loader.*
import kotlinx.android.synthetic.main.layout_placeholder.*

class ChatFragment : BaseFragment(), ChatView {

    override val layoutId: Int = R.layout.fragment_chat

    @InjectPresenter
    lateinit var presenter: ChatPresenter

    @ProvidePresenter
    fun providePresenter(): ChatPresenter = App.appComponent.provideChatPresenter()

    private lateinit var adapter: ChatAdapter
    private lateinit var placeholder: View
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var loader: View
    private lateinit var paginationLoader: ProgressBar

    override fun setUpListeners() {
        super.setUpListeners()

        refreshLayout.setOnRefreshListener {
            presenter.onSwipeToRefresh()
        }

        ibBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle: Bundle? = this.arguments
        presenter.dialogUid = bundle?.getString("dialogUid") ?: ""
    }

    override fun setUpUI() {
        placeholder = llNoMessages
        refreshLayout = srlChat
        loader = llLoading
        paginationLoader = pbChat
    }


    override fun showNewItems(newItemsCount: Int) {
        adapter.notifyItemRangeInserted(adapter.itemCount - newItemsCount, newItemsCount)
    }

    private fun callbackAdapter(chat: ArrayList<Message>) {
        adapter = ChatAdapter(chat, object : ChatAdapter.Callback {
            override fun onMessageClick(message: Message) {
                //todo действия при нажатии на сообщение
            }
        })
    }

    private fun addOnScrollListenerRecyclerView(
        recyclerView: RecyclerView,
        layoutManager: LinearLayoutManager,
        action: () -> Unit
    ) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastVisibleItemPosition() >= recyclerView.adapter!!.itemCount - 2) {
                    action.invoke()
                }
            }
        })
    }

    override fun changeLoaderVisibilityState(isVisible: Boolean) {
        val param = refreshLayout.layoutParams as ViewGroup.MarginLayoutParams
        if (isVisible) {
            param.setMargins(0, 0, 0, 150)
        } else {
            param.setMargins(0, 0, 0, 0)
        }
        refreshLayout.layoutParams = param
        loader.changeVisibilityState(isVisible)
    }

    override fun changePlaceholderVisibilityState(isVisible: Boolean) {
        placeholder.changeVisibilityState(isVisible)
    }

    override fun changeRefreshVisibilityState(isVisible: Boolean) {
        refreshLayout.isRefreshing = isVisible
    }

    override fun changePaginationLoaderState(isVisible: Boolean) {
        paginationLoader.changeVisibilityState(isVisible)
    }

    override fun showInitialItems(items: ArrayList<Message>) {
        callbackAdapter(items)
        rvChat.adapter = adapter
        val layoutManager = rvChat.layoutManager as LinearLayoutManager
        addOnScrollListenerRecyclerView(rvChat, layoutManager) {
            presenter.onNewPageScrolled()
        }
    }
}