package com.example.domain.gateways

interface CurrentUserGateway {
    fun getCurrentUserUid(): String?
}