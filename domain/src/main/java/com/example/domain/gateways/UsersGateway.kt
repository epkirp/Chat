package com.example.domain.gateways

interface UsersGateway {
    fun getUsersList(currentUserUid: String) : List<String>
    fun getUsernameByUid(username: String) : String?
}