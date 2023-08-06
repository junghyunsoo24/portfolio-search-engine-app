package com.example.child_emotion_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.child_emotion_app.data.Login
import com.example.child_emotion_app.databinding.ActivityLoginBinding
import com.example.child_emotion_app.model.AppViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var id: String
    private lateinit var pw: String
    private lateinit var responses: TextView

    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this)[AppViewModel::class.java]

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val login: Button = findViewById(R.id.login_btn)
        val join: Button = findViewById(R.id.join_btn)

        login.setOnClickListener {
            id = binding.idInput.text.toString()
            pw = binding.pwdInput.text.toString()
            responses = binding.loginResult
            mobileToServer()
        }

        join.setOnClickListener {
            onJoinButtonClicked()
        }
    }

    private fun showAlertDialog(message: String) {
        Log.e("AlertDialog", "message: $message")

        val builder = AlertDialog.Builder(this)
        if (message == "0") {
            builder.setTitle("로그인 성공")
            builder.setMessage("다음 화면으로 이동합니다")
            builder.setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss() // 다이얼로그를 닫습니다.
                onLoginButtonClicked() // 다음 화면으로 넘어가는 함수 호출
            }
        } else {
            builder.setTitle("로그인 실패")
            builder.setMessage("다시 입력하세요")
            builder.setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss() // 다이얼로그를 닫습니다.
            }
        }

        builder.show()
    }

    fun onLoginButtonClicked() {
        val intent = Intent(this, ChildActivity::class.java)
        intent.putExtra("userId", viewModel.getUserId().value)
        startActivity(intent)
    }

    fun onJoinButtonClicked() {
        val intent = Intent(this, RegistActivity::class.java)
        startActivity(intent)
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                val message = Login(id, pw)
                val response = MeApi.retrofitService.sendsMessage(message)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // 서버 응답을 확인하는 작업 수행
                        val responseData = responseBody.result
                        showAlertDialog(responseData)

                        viewModel.setUserId(id) // "responseData"가 "0"일 때 "id"를 ViewModel로 저장
                        viewModel.setUserPwd(pw)
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
}