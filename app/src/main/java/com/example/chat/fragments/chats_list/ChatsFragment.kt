package com.example.chat.fragments.chats_list

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
import com.example.chat.adapters.DialogsAdapter
import com.example.chat.fragments.base.BaseFragment
import com.example.chat.fragments.chat.ChatFragment
import com.example.chat.utils.changeVisibilityState
import com.example.domain.messages.Dialog
import kotlinx.android.synthetic.main.fragment_chats.*
import kotlinx.android.synthetic.main.layout_loader.*
import kotlinx.android.synthetic.main.layout_placeholder.*

class ChatsFragment : BaseFragment(), ChatsView {

    override val layoutId: Int = R.layout.fragment_chats

    @InjectPresenter
    lateinit var presenter: ChatsPresenter

    @ProvidePresenter
    fun providePresenter(): ChatsPresenter = App.appComponent.provideChatsPresenter()

    private lateinit var adapter: DialogsAdapter
    private lateinit var placeholder: View
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var loader: View
    private lateinit var paginationLoader: ProgressBar

    override fun setUpListeners() {
        super.setUpListeners()

        refreshLayout.setOnRefreshListener {
            presenter.onSwipeToRefresh()
        }
    }

    override fun setUpUI() {
        placeholder = llNoMessages
        refreshLayout = srlChats
        loader = llLoading
        paginationLoader = pbChats
    }


    override fun showNewItems(newItemsCount: Int) {
        adapter.notifyItemRangeInserted(adapter.itemCount - newItemsCount, newItemsCount)
    }

    private fun callbackAdapter(dialogs: ArrayList<Dialog>) {
        adapter = DialogsAdapter(dialogs, object : DialogsAdapter.Callback {
            override fun onDialogClick(dialog: Dialog) {

                val chatFragment = ChatFragment()
                val args = Bundle()
                args.putString("dialogUid", dialog.id)

                chatFragment.arguments = args

                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.addToBackStack(null)
                    ?.add(R.id.container, chatFragment)
                    ?.commit()
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

    override fun showInitialItems(items: ArrayList<Dialog>) {
        callbackAdapter(items)
        rvChats.adapter = adapter
        val layoutManager = rvChats.layoutManager as LinearLayoutManager
        addOnScrollListenerRecyclerView(rvChats, layoutManager) {
            presenter.onNewPageScrolled()
        }
    }
}