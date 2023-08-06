package com.example.child_emotion_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.child_emotion_app.databinding.ActivityMypageBinding
import com.example.child_emotion_app.model.AppViewModel

class MypageActivity : AppCompatActivity() {
    private lateinit var id: String
    private lateinit var viewModel: AppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_mypage)

        val binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        viewModel = AppViewModel.getInstance()

        binding.memberId.text = "아이디: " + viewModel.getUserId().value
        viewModel.getUserId().value?.let { Log.e("mypage", it) }

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