package com.example.chat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.utils.getFormattedDateToString
import com.example.domain.messages.Dialog
import kotlinx.android.synthetic.main.item_chat.view.*

class DialogsAdapter(
    private var dialogs: List<Dialog>,
    private var callback: Callback
) : RecyclerView.Adapter<DialogsAdapter.MessageViewHolder>() {

    interface Callback {
        fun onDialogClick(dialog: Dialog)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_chat, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int = dialogs.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.onBind(dialogs[position])
    }

    inner class MessageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                callback.onDialogClick(dialogs[adapterPosition])
            }
        }

        fun onBind(dialog: Dialog) {
            val lastMessage = dialog.lastMessage
            with(view) {
                //todo если отправитель - current user, то выводить recipient
                //todo выводить username вместо uid
                tvUsername.text = lastMessage.senderUsername
                tvLastMessageText.text = lastMessage.text
                tvLastMessageTime.text = getFormattedDateToString(lastMessage.time)
            }
        }
    }
}