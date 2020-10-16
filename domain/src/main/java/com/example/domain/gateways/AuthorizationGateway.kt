package com.example.domain.gateways

import io.reactivex.Completable

interface AuthorizationGateway {
    fun singIn(email: String, password: String): Completable
    fun signUp(email: String, password: String): Completable
}