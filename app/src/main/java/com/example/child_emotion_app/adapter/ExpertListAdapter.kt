package com.example.child_emotion_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.child_emotion_app.R
import com.example.child_emotion_app.data.expertList.Expert

class ExpertListAdapter(var expertList: List<Expert>) :
    RecyclerView.Adapter<ExpertListAdapter.ExpertViewHolder>() {

    class ExpertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expertInfoTextView: TextView = itemView.findViewById(R.id.expertInfoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expert, parent, false)
        return ExpertViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpertViewHolder, position: Int) {
        val expert = expertList[position]
        val expertInfo = "이름: ${expert.name}\n 기관: ${expert.institution}\n"
        holder.expertInfoTextView.text = expertInfo
    }

    override fun getItemCount() = expertList.size
}