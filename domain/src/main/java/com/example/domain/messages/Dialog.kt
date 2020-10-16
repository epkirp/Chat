package com.example.domain.messages

data class Dialog(
    val id: String,
    val lastMessage: Message,
    val users: List<String>
)