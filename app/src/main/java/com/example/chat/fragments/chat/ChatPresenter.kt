package com.example.chat.fragments.chat

import com.arellomobile.mvp.InjectViewState
import com.example.chat.fragments.base.base_pagination.BasePaginationPresenter
import com.example.domain.gateways.ChatGateway
import com.example.domain.messages.Message
import javax.inject.Inject

@InjectViewState
class ChatPresenter @Inject constructor(

    private val chatGateway: ChatGateway
) : BasePaginationPresenter<ChatView, Message>() {

    lateinit var dialogUid: String

    override fun getItems(page: Int, limit: Int) = chatGateway.getMessages(page, limit)
    override fun initValues(limit: Int) = chatGateway.setPaginationInfo(dialogUid)
}