package com.example.child_emotion_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.child_emotion_app.R
import com.example.child_emotion_app.data.childList.Child

class ChildListAdapter(var childList: List<Child>) :
    RecyclerView.Adapter<ChildListAdapter.ChildViewHolder>() {

    class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val childInfoTextView: TextView = itemView.findViewById(R.id.childInfoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_child, parent, false)
        return ChildViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val child = childList[position]
        val childInfo = "이름: ${child.name}\n 아이디: ${child.id}\n 비밀번호: ${child.pw}\n" +
                "주소: ${child.address}\n 핸드폰번호: ${child.phone_num}\n 위험 상태: ${child.at_risk_child_status}\n"+
                "감정: ${child.sentiment}\n"

        holder.childInfoTextView.text = childInfo
    }

    override fun getItemCount() = childList.size
}