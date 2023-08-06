package com.example.child_emotion_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.child_emotion_app.databinding.ActivityMypageBinding

class MypageActivity : AppCompatActivity() {
    private lateinit var userId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_mypage)

        val binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        userId = intent.getStringExtra("userId").toString()
        binding.memberId.text = "아이디: " + userId
        Log.e("mypage", userId)

        binding.logoutBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("로그아웃")
            builder.setMessage("로그아웃 완료")

            builder.setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss() // 다이얼로그를 닫습니다.
                onLogoutButtonClicked() // 다음 화면으로 넘어가는 함수 호출
            }
            builder.show()
        }
    }

    fun onLogoutButtonClicked(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}