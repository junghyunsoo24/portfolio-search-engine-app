package com.example.child_emotion_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.child_emotion_app.adapter.MessageAdapter
import com.example.child_emotion_app.data.Search
import com.example.child_emotion_app.databinding.ActivitySearchBinding
import com.example.child_emotion_app.model.AppViewModel
import com.example.child_emotion_app.service.SearchApi
import com.example.child_emotion_app.service.message.MyApi
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    private lateinit var input: String

    private lateinit var adapter: MessageAdapter
    private val messages = mutableListOf<String>()
    private lateinit var binding: ActivitySearchBinding

    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AppViewModel.getInstance()

        adapter = MessageAdapter(messages)
        binding.chatRecyclerView.adapter = adapter
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                input = binding.searchInput.text.toString()
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.searchInput.windowToken, 0)
                if (input.isNotBlank()) {
                    mobileToServer()

                    binding.searchInput.text = null
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

        Log.e("recycler", viewModel.getMessageList().value.toString())

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
                val message = Search(input)

                val response = SearchApi.retrofitService.sendsMessage(message)

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val responseData = responseBody.documents
                        messages.add(input)
                        messages.add(responseData.toString())
                        adapter.notifyDataSetChanged()

                        viewModel.setMessageList(messages)

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

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }
}