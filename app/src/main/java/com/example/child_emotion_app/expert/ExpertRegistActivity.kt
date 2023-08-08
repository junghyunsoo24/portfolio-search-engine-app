package com.example.child_emotion_app.expert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.child_emotion_app.LoginActivity
import com.example.child_emotion_app.data.regist.ExpertRegist
import com.example.child_emotion_app.databinding.ActivityExpertRegistBinding
import com.example.child_emotion_app.service.regist.ExpertRegistApi
import kotlinx.coroutines.launch

class ExpertRegistActivity : AppCompatActivity(){
    private lateinit var id: String
    private lateinit var pw: String
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var institution: String

    private lateinit var text: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityExpertRegistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        binding.expertRegistBtn.setOnClickListener {
            id = binding.firstExpertIdInput.text.toString()
            pw = binding.firstExpertPwdInput.text.toString()
            name = binding.expertNameInput.text.toString()
            email = binding.emailInput.text.toString()
            institution = binding.institutionInput.text.toString()

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
        builder.setMessage("회원가입 성공")

        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
            onRegistButtonClicked()
        }

        builder.show()
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                val message = ExpertRegist(id, pw, name, email, institution)
                val response = ExpertRegistApi.retrofitService.sendsMessage(message)
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