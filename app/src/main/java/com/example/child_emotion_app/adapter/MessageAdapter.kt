package com.example.child_emotion_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.child_emotion_app.R
import com.example.child_emotion_app.data.message.MessagePair


class MessageAdapter(private val messagePairs: List<MessagePair>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageItemTextView: TextView = itemView.findViewById(R.id.messageTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val messagePair = messagePairs[position]
        val messageInfo = "입력: ${messagePair.inputMessage}\n응답: ${messagePair.responseMessage}\n"
        holder.messageItemTextView.text = messageInfo

    }

    override fun getItemCount(): Int = messagePairs.size
}
