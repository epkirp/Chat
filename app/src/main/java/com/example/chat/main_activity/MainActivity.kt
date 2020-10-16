package com.example.chat.main_activity

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.chat.App
import com.example.chat.R
import com.example.chat.fragments.WelcomeFragment
import com.example.chat.fragments.chats_list.ChatsFragment

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = App.appComponent.provideMainPresenter()

    init {
        App.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun openWelcomeFragment() {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                WelcomeFragment()
            )
            .commit()
    }

    override fun openChatsFragment() {
        supportFragmentManager.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                ChatsFragment()
            )
            .commit()
    }
}