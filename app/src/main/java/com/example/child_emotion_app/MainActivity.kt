package com.example.child_emotion_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.child_emotion_app.databinding.ActivityMainBinding

//import com.example.navigatepractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val start : Button = findViewById(R.id.start_btn)

         binding.startBtn.setOnClickListener {
            onStartButtonClicked()
        }

    }

    fun onStartButtonClicked() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}