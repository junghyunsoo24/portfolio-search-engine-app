package com.example.child_emotion_app.expert

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.child_emotion_app.MypageActivity
import com.example.child_emotion_app.R
import com.example.child_emotion_app.adapter.OneChildStaticsAdapter
import com.example.child_emotion_app.data.statics.Emotion
import com.example.child_emotion_app.data.statics.OneChildStatics
import com.example.child_emotion_app.databinding.ActivityOneChildStaticsBinding
import com.example.child_emotion_app.service.statics.OneChildStaticsApi
import kotlinx.coroutines.launch

class OneChildStaticsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOneChildStaticsBinding

    private lateinit var result: List<Emotion>
    private lateinit var child_id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOneChildStaticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.emotionTextView.layoutManager = layoutManager
        val adapter = OneChildStaticsAdapter(emptyList())
        binding.emotionTextView.adapter = adapter

        binding.childIdInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                child_id = binding.childIdInput.text.toString()
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.childIdInput.windowToken, 0)
                if (child_id.isNotBlank()) {
                    mobileToServer()
                }
                true
            } else {
                false
            }
        }
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                val message = OneChildStatics(child_id)
                val response = OneChildStaticsApi.retrofitService.sendsMessage(message)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val responseData = responseBody.statistics
                        result = responseData

                        val adapter = binding.emotionTextView.adapter as OneChildStaticsAdapter
                        adapter.oneChildStatics = result
                        adapter.notifyDataSetChanged()

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
}
