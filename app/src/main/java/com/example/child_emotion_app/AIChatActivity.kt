package com.example.child_emotion_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.child_emotion_app.data.Message
import com.example.child_emotion_app.databinding.ActivityAichatBinding
import kotlinx.coroutines.launch

class AIChatActivity : AppCompatActivity() {
    private lateinit var input: String
//    private lateinit var responses: TextView

    private lateinit var adapter: MessageAdapter
    private val messages = mutableListOf<String>()
    private lateinit var binding: ActivityAichatBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_aichat)

        binding = ActivityAichatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MessageAdapter(messages)
        binding.chatRecyclerView.adapter = adapter
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)

//        input = binding.talkInput.text.toString()
//        responses = binding.response

        binding.talkInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                input = binding.talkInput.text.toString()
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.talkInput.windowToken, 0)
                if (input.isNotBlank()) {
                    mobileToServer()
                }
                true
            } else {
                false
            }
        }

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mypage_btn -> {
                val intent = Intent(this, MypageActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                val message = Message(input)

                val response = MyApi.retrofitService.sendMessage(message)

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // 서버 응답을 확인하는 작업 수행
                        val responseData = responseBody.bot
//                        responses.text = responseData
                        messages.add(input)
                        messages.add(responseData)
                        adapter.notifyDataSetChanged()
                        scrollToBottom()

                    } else {
                        Log.e("@@@@Error3", "Response body is null")
                    }
                } else {
                    Log.e("@@@@Error2", "Response not successful: ${response.code()}")
                }
            } catch (Ex: Exception) {
                Log.e("@@@@Error1", Ex.stackTraceToString())
            }
        }
    }

    private fun scrollToBottom() {
        binding.chatRecyclerView.post {
            binding.chatRecyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        }
    }
}
