package com.example.chat.di

import com.example.gateway.AuthorizationFirebase
import com.example.gateway.ChatFirebase
import com.example.gateway.ChatsFirebase
import com.example.gateway.CurrentUserFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module(includes = [FirebaseModule::class])
class GatewayModule {
    @Provides
    //@Singleton
    fun provideFirebaseAuthorizationGateway(firebaseAuth: FirebaseAuth): AuthorizationFirebase {
        return AuthorizationFirebase(firebaseAuth)
    }

    fun provideFirebaseChatGateway(
        firebaseUser: FirebaseUser?,
        firestore: FirebaseFirestore
    ): ChatFirebase {
        return ChatFirebase(firebaseUser, firestore)
    }

    fun provideFirebaseChatsGateway(
        firebaseUser: FirebaseUser?,
        firestore: FirebaseFirestore
    ): ChatsFirebase {
        return ChatsFirebase(firebaseUser, firestore)
    }

    fun provideFirebaseCurrentUserGateway(firebaseUser: FirebaseUser?): CurrentUserFirebase {
        return CurrentUserFirebase(firebaseUser)
    }
}