package com.example.gateway

import com.example.domain.gateways.CurrentUserGateway
import com.google.firebase.auth.FirebaseUser

class CurrentUserFirebase(private val firebaseUser: FirebaseUser?) : CurrentUserGateway {

    override fun getCurrentUserUid(): String? {
        return firebaseUser?.uid
    }
}