package com.example.child_emotion_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.child_emotion_app.R
import com.example.child_emotion_app.data.noAllocateChildList.Child


class NoAllocateChildListAdapter(var noAllocateChildList: List<Child>) :
    RecyclerView.Adapter<NoAllocateChildListAdapter.NoAllocateChildViewHolder>() {

    class NoAllocateChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noAllocatechildInfoTextView: TextView = itemView.findViewById(R.id.noAllocatechildInfoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoAllocateChildViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_no_allocate_child, parent, false)
        return NoAllocateChildViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoAllocateChildViewHolder, position: Int) {
        val child = noAllocateChildList[position]
        val childInfo = "아이디: ${child.id}\n 이름: ${child.name}\n" +
                "핸드폰번호: ${child.phone_num}\n"+
                "주소: ${child.address}\n"
        holder.noAllocatechildInfoTextView.text = childInfo
    }

    override fun getItemCount() = noAllocateChildList.size
}