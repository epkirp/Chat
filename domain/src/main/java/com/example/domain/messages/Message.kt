package com.example.domain.messages

import java.util.*

data class Message(
        val receiverUsername: String,
        val senderUsername: String,
        val text: String,
        val time: Date
)