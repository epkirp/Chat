package com.example.chat.fragments.chats_list

import com.arellomobile.mvp.InjectViewState
import com.example.chat.fragments.base.base_pagination.BasePaginationPresenter
import com.example.domain.gateways.ChatsGateway
import com.example.domain.gateways.CurrentUserGateway
import com.example.domain.gateways.UsersGateway
import com.example.domain.messages.Dialog
import javax.inject.Inject

@InjectViewState
class ChatsPresenter @Inject constructor(
    private val chatsGateway: ChatsGateway,
    private val usersGateway: UsersGateway,
    private val currentUserGateway: CurrentUserGateway
) : BasePaginationPresenter<ChatsView, Dialog>() {

    override fun getItems(page: Int, limit: Int) = chatsGateway.getChats(page, limit)
    override fun initValues(limit: Int) = chatsGateway.setPaginationInfo()

    fun getCurrentUsername(): String {
        val currentUserUid = currentUserGateway.getCurrentUserUid()
        return if (currentUserUid.isNullOrBlank()) {
            ""
        } else {
            usersGateway.getUsernameByUid(currentUserUid) ?: ""
        }
    }
}