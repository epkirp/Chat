package com.example.domain.gateways

import com.example.domain.messages.Message
import io.reactivex.Single

interface ChatGateway {
    fun getMessages(
        page: Int,
        limit: Int
    ): Single<List<Message>?>

    fun setPaginationInfo(dialogUid: String): Single<Int?>
}