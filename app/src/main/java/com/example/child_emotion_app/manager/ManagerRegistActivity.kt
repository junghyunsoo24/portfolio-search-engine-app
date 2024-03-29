package com.example.child_emotion_app.manager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.child_emotion_app.LoginActivity
import com.example.child_emotion_app.data.regist.ManagerRegist
import com.example.child_emotion_app.databinding.ActivityManagerRegistBinding
import com.example.child_emotion_app.service.regist.ManagerRegistApi
import kotlinx.coroutines.launch

class ManagerRegistActivity : AppCompatActivity(){
    private lateinit var id: String
    private lateinit var pw: String

    private lateinit var text: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityManagerRegistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        binding.managerRegistBtn.setOnClickListener {
            id = binding.firstManagerIdInput.text.toString()
            pw = binding.firstManagerPwdInput.text.toString()

            mobileToServer()
        }
    }

    fun onRegistButtonClicked(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(message)
        builder.setMessage("관리자 회원가입 성공")

        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
            onRegistButtonClicked()
        }

        builder.show()
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                val message = ManagerRegist(id, pw)
                val response = ManagerRegistApi.retrofitService.sendsMessage(message)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // 서버 응답을 확인하는 작업 수행
                        val responseData = responseBody.result

                        showAlertDialog(responseData)

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