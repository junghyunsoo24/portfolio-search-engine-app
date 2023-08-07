package com.example.child_emotion_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.child_emotion_app.databinding.ActivityMainBinding
import com.example.child_emotion_app.model.AppViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = AppViewModel.getInstance()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

         binding.childBtn.setOnClickListener {
             viewModel.setUser("0")
            onStartButtonClicked()
        }

        binding.expertBtn.setOnClickListener {
            viewModel.setUser("1")
            onStartButtonClicked()
        }

        binding.managerBtn.setOnClickListener {
            viewModel.setUser("2")
            onStartButtonClicked()
        }

        viewModel.getUser().value?.let { Log.e("user", it) }
    }

    fun onStartButtonClicked() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}