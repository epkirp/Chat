package com.example.domain.user

import java.io.Serializable
import java.util.*

data class FirebaseUserEntity(
    val email: String,
    val password: String
) : Serializable