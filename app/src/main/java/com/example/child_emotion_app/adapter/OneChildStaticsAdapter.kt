package com.example.child_emotion_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.child_emotion_app.R
import com.example.child_emotion_app.data.statics.Emotion

class OneChildStaticsAdapter(var oneChildStatics: List<Emotion>) :
    RecyclerView.Adapter<OneChildStaticsAdapter.EmotionViewHolder>() {

    class EmotionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val emotionInfoTextView: TextView = itemView.findViewById(R.id.emotionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_emotion, parent, false)
        return EmotionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmotionViewHolder, position: Int) {
        val emotion = oneChildStatics[position]
        val emotionInfo = "날짜: ${emotion.date}\n 기쁨: ${emotion.pleasure}\n 불안: ${emotion.anxiety}\n" +
                "슬픔: ${emotion.sorrow}\n 당황함: ${emotion.embarrassed}\n 화남: ${emotion.anger}\n"+
                "상처: ${emotion.hurt}\n"
        holder.emotionInfoTextView.text = emotionInfo
    }

    override fun getItemCount() = oneChildStatics.size
}