package com.example.child_emotion_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.child_emotion_app.child.ChildActivity
import com.example.child_emotion_app.child.RegistActivity
import com.example.child_emotion_app.data.login.Login
import com.example.child_emotion_app.expert.ExpertActivity
import com.example.child_emotion_app.manager.ManagerActivity
import com.example.child_emotion_app.model.AppViewModel
import com.example.child_emotion_app.service.login.MeApi
import com.example.child_emotion_app.databinding.ActivityLoginBinding
import com.example.child_emotion_app.expert.ExpertRegistActivity
import com.example.child_emotion_app.manager.ManagerRegistActivity
import com.example.child_emotion_app.service.login.ExpertLoginApi
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var id: String
    private lateinit var pw: String

    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = AppViewModel.getInstance()

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

            if(viewModel.getUser().value == "0") {
                mobileToServer()
            }
            else if(viewModel.getUser().value == "1"){
                expertMobileToServer()
            }

        }

        join.setOnClickListener {
            onJoinButtonClicked()
        }

        //viewModel.getUser().value?.let { Log.e("user", it) }
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        if (message == "0") {
            builder.setTitle("로그인 성공")
            builder.setMessage("다음 화면으로 이동합니다")
            builder.setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
                onLoginButtonClicked()
            }
        } else {
            builder.setTitle("로그인 실패")
            builder.setMessage("다시 입력하세요")
            builder.setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
            }
        }

        builder.show()
    }

    fun onLoginButtonClicked() {
        val intent: Intent
        if(viewModel.getUser().value == "0") {
            intent = Intent(this, ChildActivity::class.java)
        }
        else if(viewModel.getUser().value == "1"){
            intent = Intent(this, ExpertActivity::class.java)
        }
        else{
            intent = Intent(this, ManagerActivity::class.java)
        }
        startActivity(intent)
    }

    fun onJoinButtonClicked() {
        val intent: Intent
        if(viewModel.getUser().value == "0") {
            intent = Intent(this, RegistActivity::class.java)
        }
        else if(viewModel.getUser().value == "1"){
            intent = Intent(this, ExpertRegistActivity::class.java)
        }
        else{
            intent = Intent(this, ManagerRegistActivity::class.java)
        }
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
                        val responseData = responseBody.result
                        showAlertDialog(responseData)

                        viewModel.setUserId(id)
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

    private fun expertMobileToServer() {
        lifecycleScope.launch {
            try {
                val message = Login(id, pw)
                val response = ExpertLoginApi.retrofitService.sendsMessage(message)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val responseData = responseBody.result
                        showAlertDialog(responseData)

                        viewModel.setUserId(id)
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