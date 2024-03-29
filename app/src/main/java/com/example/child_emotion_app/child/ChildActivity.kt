package com.example.child_emotion_app.child

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.child_emotion_app.MypageActivity
import com.example.child_emotion_app.R
import com.example.child_emotion_app.databinding.ActivityChildBinding
import com.example.child_emotion_app.model.AppViewModel

class ChildActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val binding = ActivityChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AppViewModel.getInstance()

        binding.aiBtn.setOnClickListener {
            onAIButtonClicked()
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

    fun onAIButtonClicked(){
        val intent = Intent(this, AIChatActivity::class.java)
        startActivity(intent)
    }
}