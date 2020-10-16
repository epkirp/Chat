package com.example.chat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.constants.RECEIVER
import com.example.chat.constants.SENDER
import com.example.chat.utils.getFormattedDateToString
import com.example.domain.messages.Message
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatAdapter(
    private var chat: List<Message>,
    private var callback: Callback
) : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    interface Callback {
        fun onMessageClick(message: Message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val layoutId = if (viewType == SENDER) {
            R.layout.item_message_sender
        } else {
            R.layout.item_message_receiver
        }

        val view = inflater.inflate(layoutId, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int = chat.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.onBind(chat[position])
    }

    override fun getItemViewType(position: Int): Int {
        //todo передавать current user
        return if (chat[position].senderUsername == "1") {
            SENDER
        } else {
            RECEIVER
        }
    }

    inner class MessageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                callback.onMessageClick(chat[adapterPosition])
            }
        }

        fun onBind(message: Message) {
            with(view) {
                //todo если отправитель - current user, то выводить recipient
                //todo выводить username вместо uid
                tvLastMessageText.text = message.text
                tvLastMessageTime.text = getFormattedDateToString(message.time)
            }
        }
    }
}