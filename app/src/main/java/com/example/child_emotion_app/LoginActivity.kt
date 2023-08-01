package com.example.child_emotion_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val login : Button = findViewById(R.id.login_btn)

        val join : Button = findViewById(R.id.join_btn)

        login.setOnClickListener {
            onLoginButtonClicked()
        }

        join.setOnClickListener {
            onJoinButtonClicked()
        }
    }

    fun onLoginButtonClicked(){
        val intent = Intent(this, ChildActivity::class.java)
        startActivity(intent)
    }

    fun onJoinButtonClicked(){
        val intent = Intent(this, RegistActivity::class.java)
        startActivity(intent)
    }
}