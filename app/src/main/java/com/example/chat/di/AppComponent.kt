package com.example.chat.di

import com.example.chat.fragments.authorization.sign_in.SignInPresenter
import com.example.chat.fragments.authorization.sign_up.SignUpPresenter
import com.example.chat.fragments.chat.ChatPresenter
import com.example.chat.fragments.chats_list.ChatsPresenter
import com.example.chat.main_activity.MainActivity
import com.example.chat.main_activity.MainPresenter
import dagger.Component

@Component(modules = [AppModule::class, GatewayModule::class])
//@Singleton
interface AppComponent {
    fun provideSignInPresenter(): SignInPresenter
    fun provideSignUpPresenter(): SignUpPresenter
    fun provideMainPresenter(): MainPresenter
    fun provideChatsPresenter(): ChatsPresenter
    fun provideChatPresenter(): ChatPresenter

    fun inject(target: MainActivity)
}