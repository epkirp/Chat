package com.example.domain.gateways

import com.example.domain.messages.Dialog
import io.reactivex.Single

interface ChatsGateway {
    fun getChats(
        page: Int,
        limit: Int
    ): Single<List<Dialog>?>

    fun setPaginationInfo(): Single<Int?>
}